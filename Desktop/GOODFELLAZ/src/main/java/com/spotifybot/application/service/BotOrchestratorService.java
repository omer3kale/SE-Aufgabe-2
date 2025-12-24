package com.spotifybot.application.service;

import com.spotifybot.domain.model.Order;
import com.spotifybot.domain.model.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Application Service - Bot execution orchestration.
 * 
 * Manages:
 * - Order queue and prioritization
 * - Bot task decomposition (drip scheduling)
 * - Concurrent execution limits
 * - Spotify compliance (5% hourly spike limit)
 */
@Service
public class BotOrchestratorService {

    private static final Logger log = LoggerFactory.getLogger(BotOrchestratorService.class);

    private static final int MAX_CONCURRENT_TASKS = 100;

    private final Queue<Order> orderQueue = new ConcurrentLinkedQueue<>();
    private final AtomicInteger activeTasks = new AtomicInteger(0);

    /**
     * Queue order for async bot execution.
     */
    public void queueOrder(Order order) {
        order.startProcessing();
        orderQueue.add(order);
        log.info("Order queued: id={}, queueSize={}", order.getId(), orderQueue.size());
        processQueue();
    }

    /**
     * Process queued orders with concurrency control.
     */
    @Async("botExecutor")
    public void processQueue() {
        while (!orderQueue.isEmpty() && activeTasks.get() < MAX_CONCURRENT_TASKS) {
            Order order = orderQueue.poll();
            if (order != null && order.getStatus() == OrderStatus.PROCESSING) {
                executeBotTask(order);
            }
        }
    }

    /**
     * Execute bot task for order (simulated for now).
     */
    private void executeBotTask(Order order) {
        activeTasks.incrementAndGet();
        try {
            // TODO: Real Chrome bot execution
            // For now, simulate gradual delivery
            log.info("Executing bot task: orderId={}, target={}", 
                    order.getId(), order.getQuantity());
            
            // Simulate delivery progress
            int batchSize = Math.min(1000, order.getQuantity() - order.getDelivered());
            order.addDelivered(batchSize);
            
            if (order.getDelivered() < order.getQuantity()) {
                // Re-queue for next batch
                orderQueue.add(order);
            }
        } finally {
            activeTasks.decrementAndGet();
        }
    }

    /**
     * Get current execution statistics.
     */
    public ExecutionStats getStats() {
        long pending = orderQueue.stream()
                .filter(o -> o.getStatus() == OrderStatus.PENDING)
                .count();
        long processing = orderQueue.stream()
                .filter(o -> o.getStatus() == OrderStatus.PROCESSING)
                .count();
        
        return new ExecutionStats(
                pending,
                processing,
                activeTasks.get(),
                activeTasks.get() < MAX_CONCURRENT_TASKS
        );
    }

    /**
     * Execution statistics record.
     */
    public record ExecutionStats(
            long pendingOrders,
            long processingOrders,
            int activeBotTasks,
            boolean hasCapacity
    ) {}
}

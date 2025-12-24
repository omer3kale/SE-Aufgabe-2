package com.spotifybot.application.service;

import com.spotifybot.application.command.PlaceOrderCommand;
import com.spotifybot.application.response.OrderResponse;
import com.spotifybot.domain.model.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Application Service - Order management use cases.
 * 
 * Orchestrates order lifecycle: create → process → complete.
 */
@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    // In-memory store (replace with Supabase in production)
    private final Map<UUID, Order> orders = new ConcurrentHashMap<>();

    private final BotOrchestratorService orchestratorService;

    public OrderService(BotOrchestratorService orchestratorService) {
        this.orchestratorService = orchestratorService;
    }

    /**
     * Place a new order and queue for bot execution.
     */
    public OrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order.create(
                command.trackUrl(),
                command.quantity(),
                command.geoTarget(),
                command.speedTier()
        );

        orders.put(order.getId(), order);
        log.info("Order created: id={}, quantity={}, geo={}", 
                order.getId(), order.getQuantity(), order.getGeoTarget());

        // Queue for async bot execution
        orchestratorService.queueOrder(order);

        return OrderResponse.from(order);
    }

    /**
     * Get order status by ID.
     */
    public OrderResponse getOrderStatus(UUID orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        return OrderResponse.from(order);
    }

    /**
     * Cancel an order.
     */
    public void cancelOrder(UUID orderId) {
        Order order = orders.get(orderId);
        if (order == null) {
            throw new IllegalArgumentException("Order not found: " + orderId);
        }
        order.cancel();
        log.info("Order cancelled: id={}", orderId);
    }

    /**
     * Update order delivery progress (called by bot executor).
     */
    public void updateDelivery(UUID orderId, int deliveredCount) {
        Order order = orders.get(orderId);
        if (order != null) {
            order.addDelivered(deliveredCount);
            log.debug("Order progress: id={}, delivered={}/{}", 
                    orderId, order.getDelivered(), order.getQuantity());
        }
    }
}

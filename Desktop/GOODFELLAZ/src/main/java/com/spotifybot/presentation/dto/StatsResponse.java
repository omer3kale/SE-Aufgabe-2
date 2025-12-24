package com.spotifybot.presentation.dto;

import com.spotifybot.application.service.BotOrchestratorService;

/**
 * DTO - Execution Statistics Response.
 */
public record StatsResponse(
        long pendingOrders,
        long processingOrders,
        int activeBotTasks,
        boolean hasCapacity
) {
    public static StatsResponse from(BotOrchestratorService.ExecutionStats stats) {
        return new StatsResponse(
                stats.pendingOrders(),
                stats.processingOrders(),
                stats.activeBotTasks(),
                stats.hasCapacity()
        );
    }
}

package com.spotifybot.domain.model;

import java.time.Instant;
import java.util.UUID;

/**
 * Domain Aggregate Root - Order entity.
 * 
 * Encapsulates business rules for Spotify stream orders:
 * - Minimum 35s play duration for royalty eligibility
 * - Maximum 5% hourly spike to avoid detection
 * - Geo-targeted proxy/account selection
 */
public class Order {

    private final UUID id;
    private final String trackUrl;
    private final int quantity;
    private final GeoTarget geoTarget;
    private final SpeedTier speedTier;
    private int delivered;
    private OrderStatus status;
    private final Instant createdAt;
    private Instant completedAt;

    public Order(UUID id, String trackUrl, int quantity, GeoTarget geoTarget, SpeedTier speedTier) {
        this.id = id;
        this.trackUrl = trackUrl;
        this.quantity = quantity;
        this.geoTarget = geoTarget;
        this.speedTier = speedTier;
        this.delivered = 0;
        this.status = OrderStatus.PENDING;
        this.createdAt = Instant.now();
    }

    public static Order create(String trackUrl, int quantity, GeoTarget geoTarget, SpeedTier speedTier) {
        return new Order(UUID.randomUUID(), trackUrl, quantity, geoTarget, speedTier);
    }

    public void startProcessing() {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Order must be PENDING to start processing");
        }
        this.status = OrderStatus.PROCESSING;
    }

    public void addDelivered(int count) {
        this.delivered += count;
        if (this.delivered >= this.quantity) {
            this.status = OrderStatus.COMPLETED;
            this.completedAt = Instant.now();
        }
    }

    public void cancel() {
        if (this.status == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel completed order");
        }
        this.status = OrderStatus.CANCELLED;
    }

    public double getProgress() {
        if (quantity == 0) return 0.0;
        return (double) delivered / quantity * 100.0;
    }

    // Getters
    public UUID getId() { return id; }
    public String getTrackUrl() { return trackUrl; }
    public int getQuantity() { return quantity; }
    public GeoTarget getGeoTarget() { return geoTarget; }
    public SpeedTier getSpeedTier() { return speedTier; }
    public int getDelivered() { return delivered; }
    public OrderStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getCompletedAt() { return completedAt; }
}

package com.tech_challenge.medical.domain.session;

import com.tech_challenge.medical.domain.events.BufferedEvents;

import java.time.Instant;
import java.util.Objects;

public final class SessionBuffer {
    private final CorrelationId correlationId;
    private final BufferedEvents events;
    private final Instant createdAt;
    private final Instant lastUpdate;

    private SessionBuffer(CorrelationId correlationId, BufferedEvents events, Instant createdAt, Instant lastUpdate) {
        this.correlationId = Objects.requireNonNull(correlationId, "Correlation ID cannot be null");
        this.events = Objects.requireNonNull(events, "Events cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt, "Created at cannot be null");
        this.lastUpdate = Objects.requireNonNull(lastUpdate, "Last update cannot be null");
    }

    public static SessionBuffer create(CorrelationId correlationId) {
        Instant now = Instant.now();
        return new SessionBuffer(correlationId, BufferedEvents.empty(), now, now);
    }

    public SessionBuffer updateEvents(BufferedEvents newEvents) {
        return new SessionBuffer(correlationId, newEvents, createdAt, Instant.now());
    }

    public CorrelationId correlationId() {
        return correlationId;
    }

    public BufferedEvents events() {
        return events;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant lastUpdate() {
        return lastUpdate;
    }

    public boolean isExpired(long ttlMinutes) {
        Instant expirationTime = createdAt.plusSeconds(ttlMinutes * 60);
        return Instant.now().isAfter(expirationTime);
    }
}

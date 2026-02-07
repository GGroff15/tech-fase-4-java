package com.tech_challenge.medical.domain.session;

import java.util.Objects;
import java.util.UUID;

public final class CorrelationId {
    private final UUID value;

    private CorrelationId(UUID value) {
        this.value = validateAndGet(value);
    }

    public static CorrelationId generate() {
        return new CorrelationId(UUID.randomUUID());
    }

    public static CorrelationId from(UUID value) {
        return new CorrelationId(value);
    }

    public static CorrelationId from(String value) {
        try {
            return new CorrelationId(UUID.fromString(value));
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid correlation ID format: " + value, ex);
        }
    }

    private UUID validateAndGet(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Correlation ID cannot be null");
        }
        return value;
    }

    public String asString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CorrelationId that = (CorrelationId) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}

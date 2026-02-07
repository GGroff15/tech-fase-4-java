package com.tech_challenge.medical.domain.triage;

import java.util.Objects;

public final class Confidence {
    private final double value;

    private Confidence(double value) {
        this.value = validateAndGet(value);
    }

    public static Confidence of(double value) {
        return new Confidence(value);
    }

    private double validateAndGet(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("Confidence must be between 0.0 and 1.0, got: " + value);
        }
        return value;
    }

    public double asDouble() {
        return value;
    }

    public boolean isHigh() {
        return value >= 0.8;
    }

    public boolean isMedium() {
        return value >= 0.5 && value < 0.8;
    }

    public boolean isLow() {
        return value < 0.5;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Confidence that = (Confidence) obj;
        return Double.compare(that.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return String.format("%.2f", value);
    }
}

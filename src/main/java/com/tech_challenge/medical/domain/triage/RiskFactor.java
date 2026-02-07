package com.tech_challenge.medical.domain.triage;

public final class RiskFactor {
    private final String description;

    private RiskFactor(String description) {
        this.description = validateAndGet(description);
    }

    public static RiskFactor of(String description) {
        return new RiskFactor(description);
    }

    private String validateAndGet(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Risk factor description cannot be null or blank");
        }
        return description.trim();
    }

    public String asString() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RiskFactor that = (RiskFactor) obj;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return description.hashCode();
    }

    @Override
    public String toString() {
        return description;
    }
}

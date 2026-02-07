package com.tech_challenge.medical.domain.triage;

public final class Inconsistency {
    private final String description;

    private Inconsistency(String description) {
        this.description = validateAndGet(description);
    }

    public static Inconsistency of(String description) {
        return new Inconsistency(description);
    }

    private String validateAndGet(String description) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Inconsistency description cannot be null or blank");
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
        Inconsistency that = (Inconsistency) obj;
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

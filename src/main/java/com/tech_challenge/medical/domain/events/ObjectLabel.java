package com.tech_challenge.medical.domain.events;

public final class ObjectLabel {
    private final String value;

    private ObjectLabel(String value) {
        this.value = validateAndGet(value);
    }

    public static ObjectLabel of(String value) {
        return new ObjectLabel(value);
    }

    private String validateAndGet(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Object label cannot be null or blank");
        }
        return value.trim();
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ObjectLabel that = (ObjectLabel) obj;
        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}

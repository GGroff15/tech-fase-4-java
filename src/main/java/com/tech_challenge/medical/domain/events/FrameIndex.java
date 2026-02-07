package com.tech_challenge.medical.domain.events;

public final class FrameIndex {
    private final long value;

    private FrameIndex(long value) {
        this.value = validateAndGet(value);
    }

    public static FrameIndex of(long value) {
        return new FrameIndex(value);
    }

    private long validateAndGet(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("Frame index cannot be negative: " + value);
        }
        return value;
    }

    public long asLong() {
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
        FrameIndex that = (FrameIndex) obj;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

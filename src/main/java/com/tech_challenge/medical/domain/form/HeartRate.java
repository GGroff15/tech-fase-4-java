package com.tech_challenge.medical.domain.form;

public final class HeartRate {
    private final int beatsPerMinute;

    private HeartRate(int beatsPerMinute) {
        this.beatsPerMinute = validateAndGet(beatsPerMinute);
    }

    public static HeartRate of(int beatsPerMinute) {
        return new HeartRate(beatsPerMinute);
    }

    private int validateAndGet(int value) {
        if (value < 0 || value > 300) {
            throw new IllegalArgumentException("Heart rate must be between 0 and 300 bpm: " + value);
        }
        return value;
    }

    public int asInt() {
        return beatsPerMinute;
    }

    public boolean isAbnormal(int normalMin, int normalMax) {
        return beatsPerMinute < normalMin || beatsPerMinute > normalMax;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HeartRate that = (HeartRate) obj;
        return beatsPerMinute == that.beatsPerMinute;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(beatsPerMinute);
    }

    @Override
    public String toString() {
        return beatsPerMinute + " bpm";
    }
}

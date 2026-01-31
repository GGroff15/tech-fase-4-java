package com.tech_challenge.medical.domain.form;

public final class RespiratoryRate {
    private final int breathsPerMinute;

    private RespiratoryRate(int breathsPerMinute) {
        this.breathsPerMinute = validateAndGet(breathsPerMinute);
    }

    public static RespiratoryRate of(int breathsPerMinute) {
        return new RespiratoryRate(breathsPerMinute);
    }

    private int validateAndGet(int value) {
        if (value < 0 || value > 60) {
            throw new IllegalArgumentException("Respiratory rate must be between 0 and 60 breaths/min: " + value);
        }
        return value;
    }

    public int asInt() {
        return breathsPerMinute;
    }

    public boolean isAbnormal(int normalMin, int normalMax) {
        return breathsPerMinute < normalMin || breathsPerMinute > normalMax;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        RespiratoryRate that = (RespiratoryRate) obj;
        return breathsPerMinute == that.breathsPerMinute;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(breathsPerMinute);
    }

    @Override
    public String toString() {
        return breathsPerMinute + " breaths/min";
    }
}

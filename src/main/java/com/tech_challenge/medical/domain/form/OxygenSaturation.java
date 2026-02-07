package com.tech_challenge.medical.domain.form;

public final class OxygenSaturation {
    private final int percentage;

    private OxygenSaturation(int percentage) {
        this.percentage = validateAndGet(percentage);
    }

    public static OxygenSaturation of(int percentage) {
        return new OxygenSaturation(percentage);
    }

    private int validateAndGet(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Oxygen saturation must be between 0 and 100%: " + value);
        }
        return value;
    }

    public int asInt() {
        return percentage;
    }

    public boolean isLow(int lowThreshold) {
        return percentage < lowThreshold;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OxygenSaturation that = (OxygenSaturation) obj;
        return percentage == that.percentage;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(percentage);
    }

    @Override
    public String toString() {
        return percentage + "%";
    }
}

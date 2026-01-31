package com.tech_challenge.medical.domain.form;

public final class Temperature {
    private final double celsius;

    private Temperature(double celsius) {
        this.celsius = validateAndGet(celsius);
    }

    public static Temperature ofCelsius(double celsius) {
        return new Temperature(celsius);
    }

    private double validateAndGet(double value) {
        if (value < 25.0 || value > 45.0) {
            throw new IllegalArgumentException("Temperature must be between 25.0°C and 45.0°C: " + value);
        }
        return value;
    }

    public double asDouble() {
        return celsius;
    }

    public boolean isFever(double feverThreshold) {
        return celsius >= feverThreshold;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Temperature that = (Temperature) obj;
        return Double.compare(that.celsius, celsius) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(celsius);
    }

    @Override
    public String toString() {
        return String.format("%.1f°C", celsius);
    }
}

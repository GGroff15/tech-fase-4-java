package com.tech_challenge.medical.domain.reference;

/**
 * Domain value object holding all vital sign threshold values.
 * Acts as a bridge between infrastructure configuration and domain logic.
 */
public final class VitalSignThresholds {
    private final HeartRateThreshold heartRate;
    private final TemperatureThreshold temperature;
    private final BloodPressureThreshold bloodPressure;
    private final OxygenSaturationThreshold oxygenSaturation;
    private final RespiratoryRateThreshold respiratoryRate;

    private VitalSignThresholds(
            HeartRateThreshold heartRate,
            TemperatureThreshold temperature,
            BloodPressureThreshold bloodPressure,
            OxygenSaturationThreshold oxygenSaturation,
            RespiratoryRateThreshold respiratoryRate
    ) {
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.bloodPressure = bloodPressure;
        this.oxygenSaturation = oxygenSaturation;
        this.respiratoryRate = respiratoryRate;
    }

    public static VitalSignThresholds of(
            HeartRateThreshold heartRate,
            TemperatureThreshold temperature,
            BloodPressureThreshold bloodPressure,
            OxygenSaturationThreshold oxygenSaturation,
            RespiratoryRateThreshold respiratoryRate
    ) {
        return new VitalSignThresholds(heartRate, temperature, bloodPressure, oxygenSaturation, respiratoryRate);
    }

    public HeartRateThreshold heartRate() {
        return heartRate;
    }

    public TemperatureThreshold temperature() {
        return temperature;
    }

    public BloodPressureThreshold bloodPressure() {
        return bloodPressure;
    }

    public OxygenSaturationThreshold oxygenSaturation() {
        return oxygenSaturation;
    }

    public RespiratoryRateThreshold respiratoryRate() {
        return respiratoryRate;
    }

    public record HeartRateThreshold(int normalMin, int normalMax) {
        public static HeartRateThreshold of(int normalMin, int normalMax) {
            return new HeartRateThreshold(normalMin, normalMax);
        }
    }

    public record TemperatureThreshold(double feverThreshold) {
        public static TemperatureThreshold of(double feverThreshold) {
            return new TemperatureThreshold(feverThreshold);
        }
    }

    public record BloodPressureThreshold(int highSystolic, int highDiastolic) {
        public static BloodPressureThreshold of(int highSystolic, int highDiastolic) {
            return new BloodPressureThreshold(highSystolic, highDiastolic);
        }
    }

    public record OxygenSaturationThreshold(int lowThreshold) {
        public static OxygenSaturationThreshold of(int lowThreshold) {
            return new OxygenSaturationThreshold(lowThreshold);
        }
    }

    public record RespiratoryRateThreshold(int normalMin, int normalMax) {
        public static RespiratoryRateThreshold of(int normalMin, int normalMax) {
            return new RespiratoryRateThreshold(normalMin, normalMax);
        }
    }
}

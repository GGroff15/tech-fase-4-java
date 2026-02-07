package com.tech_challenge.medical.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "medical.vitals")
public class VitalsReferenceProperties {
    private HeartRateConfig heartRate;
    private TemperatureConfig temperature;
    private BloodPressureConfig bloodPressure;
    private OxygenSaturationConfig oxygenSaturation;
    private RespiratoryRateConfig respiratoryRate;

    public HeartRateConfig heartRate() {
        return heartRate;
    }

    public TemperatureConfig temperature() {
        return temperature;
    }

    public BloodPressureConfig bloodPressure() {
        return bloodPressure;
    }

    public OxygenSaturationConfig oxygenSaturation() {
        return oxygenSaturation;
    }

    public RespiratoryRateConfig respiratoryRate() {
        return respiratoryRate;
    }

    public void setHeartRate(HeartRateConfig heartRate) {
        this.heartRate = heartRate;
    }

    public void setTemperature(TemperatureConfig temperature) {
        this.temperature = temperature;
    }

    public void setBloodPressure(BloodPressureConfig bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public void setOxygenSaturation(OxygenSaturationConfig oxygenSaturation) {
        this.oxygenSaturation = oxygenSaturation;
    }

    public void setRespiratoryRate(RespiratoryRateConfig respiratoryRate) {
        this.respiratoryRate = respiratoryRate;
    }

    public static class HeartRateConfig {
        private int normalMin;
        private int normalMax;

        public int normalMin() {
            return normalMin;
        }

        public int normalMax() {
            return normalMax;
        }

        public void setNormalMin(int normalMin) {
            this.normalMin = normalMin;
        }

        public void setNormalMax(int normalMax) {
            this.normalMax = normalMax;
        }
    }

    public static class TemperatureConfig {
        private double feverThreshold;

        public double feverThreshold() {
            return feverThreshold;
        }

        public void setFeverThreshold(double feverThreshold) {
            this.feverThreshold = feverThreshold;
        }
    }

    public static class BloodPressureConfig {
        private int highSystolic;
        private int highDiastolic;

        public int highSystolic() {
            return highSystolic;
        }

        public int highDiastolic() {
            return highDiastolic;
        }

        public void setHighSystolic(int highSystolic) {
            this.highSystolic = highSystolic;
        }

        public void setHighDiastolic(int highDiastolic) {
            this.highDiastolic = highDiastolic;
        }
    }

    public static class OxygenSaturationConfig {
        private int lowThreshold;

        public int lowThreshold() {
            return lowThreshold;
        }

        public void setLowThreshold(int lowThreshold) {
            this.lowThreshold = lowThreshold;
        }
    }

    public static class RespiratoryRateConfig {
        private int normalMin;
        private int normalMax;

        public int normalMin() {
            return normalMin;
        }

        public int normalMax() {
            return normalMax;
        }

        public void setNormalMin(int normalMin) {
            this.normalMin = normalMin;
        }

        public void setNormalMax(int normalMax) {
            this.normalMax = normalMax;
        }
    }
}

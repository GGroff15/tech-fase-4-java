package com.tech_challenge.medical.domain.form;

import java.util.HashMap;
import java.util.Map;

public final class VitalSigns {
    private final HeartRate heartRate;
    private final BloodPressure bloodPressure;
    private final Temperature temperature;
    private final OxygenSaturation oxygenSaturation;
    private final RespiratoryRate respiratoryRate;

    private VitalSigns(
            HeartRate heartRate,
            BloodPressure bloodPressure,
            Temperature temperature,
            OxygenSaturation oxygenSaturation,
            RespiratoryRate respiratoryRate
    ) {
        this.heartRate = heartRate;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
        this.oxygenSaturation = oxygenSaturation;
        this.respiratoryRate = respiratoryRate;
    }

    public static VitalSigns of(
            HeartRate heartRate,
            BloodPressure bloodPressure,
            Temperature temperature,
            OxygenSaturation oxygenSaturation,
            RespiratoryRate respiratoryRate
    ) {
        return new VitalSigns(heartRate, bloodPressure, temperature, oxygenSaturation, respiratoryRate);
    }

    public HeartRate heartRate() {
        return heartRate;
    }

    public BloodPressure bloodPressure() {
        return bloodPressure;
    }

    public Temperature temperature() {
        return temperature;
    }

    public OxygenSaturation oxygenSaturation() {
        return oxygenSaturation;
    }

    public RespiratoryRate respiratoryRate() {
        return respiratoryRate;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("heartRate", heartRate != null ? heartRate.asInt() : null);
        map.put("bloodPressure", bloodPressure != null ? bloodPressure.asString() : null);
        map.put("temperature", temperature != null ? temperature.asDouble() : null);
        map.put("oxygenSaturation", oxygenSaturation != null ? oxygenSaturation.asInt() : null);
        map.put("respiratoryRate", respiratoryRate != null ? respiratoryRate.asInt() : null);
        return map;
    }
}

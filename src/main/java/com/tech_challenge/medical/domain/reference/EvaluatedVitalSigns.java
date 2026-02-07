package com.tech_challenge.medical.domain.reference;

import java.util.HashMap;
import java.util.Map;

/**
 * Aggregate containing evaluated vital signs compared against reference ranges.
 */
public final class EvaluatedVitalSigns {
    private final EvaluatedVitalSign heartRate;
    private final EvaluatedVitalSign systolicBloodPressure;
    private final EvaluatedVitalSign diastolicBloodPressure;
    private final EvaluatedVitalSign temperature;
    private final EvaluatedVitalSign oxygenSaturation;
    private final EvaluatedVitalSign respiratoryRate;

    private EvaluatedVitalSigns(
            EvaluatedVitalSign heartRate,
            EvaluatedVitalSign systolicBloodPressure,
            EvaluatedVitalSign diastolicBloodPressure,
            EvaluatedVitalSign temperature,
            EvaluatedVitalSign oxygenSaturation,
            EvaluatedVitalSign respiratoryRate
    ) {
        this.heartRate = heartRate;
        this.systolicBloodPressure = systolicBloodPressure;
        this.diastolicBloodPressure = diastolicBloodPressure;
        this.temperature = temperature;
        this.oxygenSaturation = oxygenSaturation;
        this.respiratoryRate = respiratoryRate;
    }

    public static EvaluatedVitalSigns of(
            EvaluatedVitalSign heartRate,
            EvaluatedVitalSign systolicBloodPressure,
            EvaluatedVitalSign diastolicBloodPressure,
            EvaluatedVitalSign temperature,
            EvaluatedVitalSign oxygenSaturation,
            EvaluatedVitalSign respiratoryRate
    ) {
        return new EvaluatedVitalSigns(
                heartRate,
                systolicBloodPressure,
                diastolicBloodPressure,
                temperature,
                oxygenSaturation,
                respiratoryRate
        );
    }

    public EvaluatedVitalSign heartRate() {
        return heartRate;
    }

    public EvaluatedVitalSign systolicBloodPressure() {
        return systolicBloodPressure;
    }

    public EvaluatedVitalSign diastolicBloodPressure() {
        return diastolicBloodPressure;
    }

    public EvaluatedVitalSign temperature() {
        return temperature;
    }

    public EvaluatedVitalSign oxygenSaturation() {
        return oxygenSaturation;
    }

    public EvaluatedVitalSign respiratoryRate() {
        return respiratoryRate;
    }

    public boolean hasAnyAbnormal() {
        return hasAbnormal(heartRate)
                || hasAbnormal(systolicBloodPressure)
                || hasAbnormal(diastolicBloodPressure)
                || hasAbnormal(temperature)
                || hasAbnormal(oxygenSaturation)
                || hasAbnormal(respiratoryRate);
    }

    private boolean hasAbnormal(EvaluatedVitalSign sign) {
        return sign != null && sign.isAbnormal();
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        addIfPresent(map, "heartRate", heartRate);
        addIfPresent(map, "systolicBloodPressure", systolicBloodPressure);
        addIfPresent(map, "diastolicBloodPressure", diastolicBloodPressure);
        addIfPresent(map, "temperature", temperature);
        addIfPresent(map, "oxygenSaturation", oxygenSaturation);
        addIfPresent(map, "respiratoryRate", respiratoryRate);
        map.put("hasAnyAbnormal", hasAnyAbnormal());
        return map;
    }

    private void addIfPresent(Map<String, Object> map, String key, EvaluatedVitalSign sign) {
        if (sign != null) {
            map.put(key, sign.asMap());
        }
    }
}

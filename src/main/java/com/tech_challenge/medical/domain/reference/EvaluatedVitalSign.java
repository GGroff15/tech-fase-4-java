package com.tech_challenge.medical.domain.reference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Value object representing a vital sign value evaluated against its reference range.
 */
public final class EvaluatedVitalSign {
    private final String name;
    private final double value;
    private final RangeStatus status;
    private final double referenceMin;
    private final double referenceMax;
    private final String unit;

    private EvaluatedVitalSign(
            String name,
            double value,
            RangeStatus status,
            double referenceMin,
            double referenceMax,
            String unit
    ) {
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        this.value = value;
        this.status = Objects.requireNonNull(status, "Status cannot be null");
        this.referenceMin = referenceMin;
        this.referenceMax = referenceMax;
        this.unit = Objects.requireNonNull(unit, "Unit cannot be null");
    }

    public static EvaluatedVitalSign of(
            String name,
            double value,
            RangeStatus status,
            double referenceMin,
            double referenceMax,
            String unit
    ) {
        return new EvaluatedVitalSign(name, value, status, referenceMin, referenceMax, unit);
    }

    public static EvaluatedVitalSign forRange(
            String name,
            double value,
            double normalMin,
            double normalMax,
            String unit
    ) {
        RangeStatus status = evaluateRange(value, normalMin, normalMax);
        return new EvaluatedVitalSign(name, value, status, normalMin, normalMax, unit);
    }

    public static EvaluatedVitalSign forLowThreshold(
            String name,
            double value,
            double lowThreshold,
            String unit
    ) {
        RangeStatus status = value < lowThreshold ? RangeStatus.BELOW_NORMAL : RangeStatus.NORMAL;
        return new EvaluatedVitalSign(name, value, status, lowThreshold, 100.0, unit);
    }

    public static EvaluatedVitalSign forHighThreshold(
            String name,
            double value,
            double highThreshold,
            String unit
    ) {
        RangeStatus status = value >= highThreshold ? RangeStatus.ABOVE_NORMAL : RangeStatus.NORMAL;
        return new EvaluatedVitalSign(name, value, status, 0.0, highThreshold, unit);
    }

    private static RangeStatus evaluateRange(double value, double min, double max) {
        if (value < min) {
            return RangeStatus.BELOW_NORMAL;
        }
        if (value > max) {
            return RangeStatus.ABOVE_NORMAL;
        }
        return RangeStatus.NORMAL;
    }

    public String name() {
        return name;
    }

    public double value() {
        return value;
    }

    public RangeStatus status() {
        return status;
    }

    public double referenceMin() {
        return referenceMin;
    }

    public double referenceMax() {
        return referenceMax;
    }

    public String unit() {
        return unit;
    }

    public boolean isAbnormal() {
        return status != RangeStatus.NORMAL;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("value", value);
        map.put("status", status.displayName());
        map.put("referenceMin", referenceMin);
        map.put("referenceMax", referenceMax);
        map.put("unit", unit);
        return map;
    }
}

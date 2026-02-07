package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.form.VitalSigns;
import com.tech_challenge.medical.domain.reference.EvaluatedVitalSign;
import com.tech_challenge.medical.domain.reference.EvaluatedVitalSigns;
import com.tech_challenge.medical.infrastructure.config.VitalsReferenceProperties;
import org.springframework.stereotype.Service;

/**
 * Service that evaluates vital signs against configurable reference ranges.
 */
@Service
public class ReferenceEvaluationService {
    private final VitalsReferenceProperties properties;

    public ReferenceEvaluationService(VitalsReferenceProperties properties) {
        this.properties = properties;
    }

    public EvaluatedVitalSigns evaluate(VitalSigns vitalSigns) {
        if (vitalSigns == null) {
            return createEmptyEvaluation();
        }

        return EvaluatedVitalSigns.of(
                evaluateHeartRate(vitalSigns),
                evaluateSystolicBloodPressure(vitalSigns),
                evaluateDiastolicBloodPressure(vitalSigns),
                evaluateTemperature(vitalSigns),
                evaluateOxygenSaturation(vitalSigns),
                evaluateRespiratoryRate(vitalSigns)
        );
    }

    private EvaluatedVitalSign evaluateHeartRate(VitalSigns vitalSigns) {
        if (vitalSigns.heartRate() == null) {
            return null;
        }
        
        var config = properties.heartRate();
        return EvaluatedVitalSign.forRange(
                "heartRate",
                vitalSigns.heartRate().asInt(),
                config.normalMin(),
                config.normalMax(),
                "bpm"
        );
    }

    private EvaluatedVitalSign evaluateSystolicBloodPressure(VitalSigns vitalSigns) {
        if (vitalSigns.bloodPressure() == null) {
            return null;
        }
        
        var config = properties.bloodPressure();
        return EvaluatedVitalSign.forHighThreshold(
                "systolicBloodPressure",
                vitalSigns.bloodPressure().systolic(),
                config.highSystolic(),
                "mmHg"
        );
    }

    private EvaluatedVitalSign evaluateDiastolicBloodPressure(VitalSigns vitalSigns) {
        if (vitalSigns.bloodPressure() == null) {
            return null;
        }
        
        var config = properties.bloodPressure();
        return EvaluatedVitalSign.forHighThreshold(
                "diastolicBloodPressure",
                vitalSigns.bloodPressure().diastolic(),
                config.highDiastolic(),
                "mmHg"
        );
    }

    private EvaluatedVitalSign evaluateTemperature(VitalSigns vitalSigns) {
        if (vitalSigns.temperature() == null) {
            return null;
        }
        
        var config = properties.temperature();
        return EvaluatedVitalSign.forHighThreshold(
                "temperature",
                vitalSigns.temperature().asDouble(),
                config.feverThreshold(),
                "°C"
        );
    }

    private EvaluatedVitalSign evaluateOxygenSaturation(VitalSigns vitalSigns) {
        if (vitalSigns.oxygenSaturation() == null) {
            return null;
        }
        
        var config = properties.oxygenSaturation();
        return EvaluatedVitalSign.forLowThreshold(
                "oxygenSaturation",
                vitalSigns.oxygenSaturation().asInt(),
                config.lowThreshold(),
                "%"
        );
    }

    private EvaluatedVitalSign evaluateRespiratoryRate(VitalSigns vitalSigns) {
        if (vitalSigns.respiratoryRate() == null) {
            return null;
        }
        
        var config = properties.respiratoryRate();
        return EvaluatedVitalSign.forRange(
                "respiratoryRate",
                vitalSigns.respiratoryRate().asInt(),
                config.normalMin(),
                config.normalMax(),
                "breaths/min"
        );
    }

    private EvaluatedVitalSigns createEmptyEvaluation() {
        return EvaluatedVitalSigns.of(null, null, null, null, null, null);
    }
}

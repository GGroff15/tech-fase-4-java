package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.form.*;
import com.tech_challenge.medical.domain.reference.EvaluatedVitalSigns;
import com.tech_challenge.medical.domain.reference.RangeStatus;
import com.tech_challenge.medical.infrastructure.config.VitalsReferenceProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ReferenceEvaluationService tests")
class ReferenceEvaluationServiceTest {

    private ReferenceEvaluationService service;

    @BeforeEach
    void setUp() {
        VitalsReferenceProperties properties = createDefaultProperties();
        service = new ReferenceEvaluationService(properties);
    }

    @Nested
    @DisplayName("when evaluating heart rate")
    class HeartRateEvaluation {

        @Test
        @DisplayName("marks normal heart rate as NORMAL")
        void normalHeartRate() {
            VitalSigns vitals = createVitalSignsWithHeartRate(75);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.NORMAL, result.heartRate().status());
        }

        @Test
        @DisplayName("marks low heart rate as BELOW_NORMAL")
        void lowHeartRate() {
            VitalSigns vitals = createVitalSignsWithHeartRate(55);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.BELOW_NORMAL, result.heartRate().status());
        }

        @Test
        @DisplayName("marks high heart rate as ABOVE_NORMAL")
        void highHeartRate() {
            VitalSigns vitals = createVitalSignsWithHeartRate(110);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.ABOVE_NORMAL, result.heartRate().status());
        }
    }

    @Nested
    @DisplayName("when evaluating temperature")
    class TemperatureEvaluation {

        @Test
        @DisplayName("marks normal temperature as NORMAL")
        void normalTemperature() {
            VitalSigns vitals = createVitalSignsWithTemperature(36.8);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.NORMAL, result.temperature().status());
        }

        @Test
        @DisplayName("marks fever temperature as ABOVE_NORMAL")
        void feverTemperature() {
            VitalSigns vitals = createVitalSignsWithTemperature(38.5);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.ABOVE_NORMAL, result.temperature().status());
        }
    }

    @Nested
    @DisplayName("when evaluating blood pressure")
    class BloodPressureEvaluation {

        @Test
        @DisplayName("marks normal blood pressure as NORMAL")
        void normalBloodPressure() {
            VitalSigns vitals = createVitalSignsWithBloodPressure(120, 80);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertAll(
                    () -> assertEquals(RangeStatus.NORMAL, result.systolicBloodPressure().status()),
                    () -> assertEquals(RangeStatus.NORMAL, result.diastolicBloodPressure().status())
            );
        }

        @Test
        @DisplayName("marks high systolic as ABOVE_NORMAL")
        void highSystolic() {
            VitalSigns vitals = createVitalSignsWithBloodPressure(150, 80);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.ABOVE_NORMAL, result.systolicBloodPressure().status());
        }

        @Test
        @DisplayName("marks high diastolic as ABOVE_NORMAL")
        void highDiastolic() {
            VitalSigns vitals = createVitalSignsWithBloodPressure(120, 95);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.ABOVE_NORMAL, result.diastolicBloodPressure().status());
        }
    }

    @Nested
    @DisplayName("when evaluating oxygen saturation")
    class OxygenSaturationEvaluation {

        @Test
        @DisplayName("marks normal oxygen saturation as NORMAL")
        void normalOxygenSaturation() {
            VitalSigns vitals = createVitalSignsWithOxygenSaturation(98);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.NORMAL, result.oxygenSaturation().status());
        }

        @Test
        @DisplayName("marks low oxygen saturation as BELOW_NORMAL")
        void lowOxygenSaturation() {
            VitalSigns vitals = createVitalSignsWithOxygenSaturation(92);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.BELOW_NORMAL, result.oxygenSaturation().status());
        }
    }

    @Nested
    @DisplayName("when evaluating respiratory rate")
    class RespiratoryRateEvaluation {

        @Test
        @DisplayName("marks normal respiratory rate as NORMAL")
        void normalRespiratoryRate() {
            VitalSigns vitals = createVitalSignsWithRespiratoryRate(16);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.NORMAL, result.respiratoryRate().status());
        }

        @Test
        @DisplayName("marks low respiratory rate as BELOW_NORMAL")
        void lowRespiratoryRate() {
            VitalSigns vitals = createVitalSignsWithRespiratoryRate(10);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.BELOW_NORMAL, result.respiratoryRate().status());
        }

        @Test
        @DisplayName("marks high respiratory rate as ABOVE_NORMAL")
        void highRespiratoryRate() {
            VitalSigns vitals = createVitalSignsWithRespiratoryRate(25);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertEquals(RangeStatus.ABOVE_NORMAL, result.respiratoryRate().status());
        }
    }

    @Nested
    @DisplayName("when handling null values")
    class NullValueHandling {

        @Test
        @DisplayName("returns empty evaluation for null vital signs")
        void nullVitalSigns() {
            EvaluatedVitalSigns result = service.evaluate(null);

            assertAll(
                    () -> assertNull(result.heartRate()),
                    () -> assertNull(result.temperature()),
                    () -> assertNull(result.systolicBloodPressure()),
                    () -> assertNull(result.oxygenSaturation()),
                    () -> assertNull(result.respiratoryRate())
            );
        }

        @Test
        @DisplayName("handles partial vital signs")
        void partialVitalSigns() {
            VitalSigns vitals = VitalSigns.of(
                    HeartRate.of(75),
                    null,
                    null,
                    null,
                    null
            );

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertAll(
                    () -> assertNotNull(result.heartRate()),
                    () -> assertNull(result.temperature()),
                    () -> assertNull(result.systolicBloodPressure()),
                    () -> assertNull(result.oxygenSaturation()),
                    () -> assertNull(result.respiratoryRate())
            );
        }
    }

    @Nested
    @DisplayName("when checking hasAnyAbnormal")
    class HasAnyAbnormalTests {

        @Test
        @DisplayName("returns false when all vitals are normal")
        void allNormal() {
            VitalSigns vitals = createNormalVitalSigns();

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertFalse(result.hasAnyAbnormal());
        }

        @Test
        @DisplayName("returns true when any vital is abnormal")
        void anyAbnormal() {
            VitalSigns vitals = createVitalSignsWithHeartRate(110);

            EvaluatedVitalSigns result = service.evaluate(vitals);

            assertTrue(result.hasAnyAbnormal());
        }
    }

    private VitalsReferenceProperties createDefaultProperties() {
        VitalsReferenceProperties properties = new VitalsReferenceProperties();

        VitalsReferenceProperties.HeartRateConfig heartRate = new VitalsReferenceProperties.HeartRateConfig();
        heartRate.setNormalMin(60);
        heartRate.setNormalMax(100);
        properties.setHeartRate(heartRate);

        VitalsReferenceProperties.TemperatureConfig temperature = new VitalsReferenceProperties.TemperatureConfig();
        temperature.setFeverThreshold(37.5);
        properties.setTemperature(temperature);

        VitalsReferenceProperties.BloodPressureConfig bloodPressure = new VitalsReferenceProperties.BloodPressureConfig();
        bloodPressure.setHighSystolic(140);
        bloodPressure.setHighDiastolic(90);
        properties.setBloodPressure(bloodPressure);

        VitalsReferenceProperties.OxygenSaturationConfig oxygenSaturation = new VitalsReferenceProperties.OxygenSaturationConfig();
        oxygenSaturation.setLowThreshold(95);
        properties.setOxygenSaturation(oxygenSaturation);

        VitalsReferenceProperties.RespiratoryRateConfig respiratoryRate = new VitalsReferenceProperties.RespiratoryRateConfig();
        respiratoryRate.setNormalMin(12);
        respiratoryRate.setNormalMax(20);
        properties.setRespiratoryRate(respiratoryRate);

        return properties;
    }

    private VitalSigns createNormalVitalSigns() {
        return VitalSigns.of(
                HeartRate.of(75),
                BloodPressure.of(120, 80),
                Temperature.ofCelsius(36.8),
                OxygenSaturation.of(98),
                RespiratoryRate.of(16)
        );
    }

    private VitalSigns createVitalSignsWithHeartRate(int heartRate) {
        return VitalSigns.of(
                HeartRate.of(heartRate),
                BloodPressure.of(120, 80),
                Temperature.ofCelsius(36.8),
                OxygenSaturation.of(98),
                RespiratoryRate.of(16)
        );
    }

    private VitalSigns createVitalSignsWithTemperature(double temperature) {
        return VitalSigns.of(
                HeartRate.of(75),
                BloodPressure.of(120, 80),
                Temperature.ofCelsius(temperature),
                OxygenSaturation.of(98),
                RespiratoryRate.of(16)
        );
    }

    private VitalSigns createVitalSignsWithBloodPressure(int systolic, int diastolic) {
        return VitalSigns.of(
                HeartRate.of(75),
                BloodPressure.of(systolic, diastolic),
                Temperature.ofCelsius(36.8),
                OxygenSaturation.of(98),
                RespiratoryRate.of(16)
        );
    }

    private VitalSigns createVitalSignsWithOxygenSaturation(int oxygenSaturation) {
        return VitalSigns.of(
                HeartRate.of(75),
                BloodPressure.of(120, 80),
                Temperature.ofCelsius(36.8),
                OxygenSaturation.of(oxygenSaturation),
                RespiratoryRate.of(16)
        );
    }

    private VitalSigns createVitalSignsWithRespiratoryRate(int respiratoryRate) {
        return VitalSigns.of(
                HeartRate.of(75),
                BloodPressure.of(120, 80),
                Temperature.ofCelsius(36.8),
                OxygenSaturation.of(98),
                RespiratoryRate.of(respiratoryRate)
        );
    }
}

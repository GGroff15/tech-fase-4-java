package com.tech_challenge.medical.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Vital signs measured or reported by patient")
public record VitalSignsDto(
        @Schema(description = "Heart rate in beats per minute", example = "72") Integer heartRate,
        @Schema(description = "Systolic blood pressure", example = "120") Integer systolicBp,
        @Schema(description = "Diastolic blood pressure", example = "80") Integer diastolicBp,
        @Schema(description = "Body temperature in Celsius", example = "36.6") Double temperature,
        @Schema(description = "Oxygen saturation percentage", example = "98") Integer oxygenSaturation,
        @Schema(description = "Respiratory rate in breaths per minute", example = "16") Integer respiratoryRate
) {
}

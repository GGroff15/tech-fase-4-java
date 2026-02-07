package com.tech_challenge.medical.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Clinical form submitted by frontend")
public record ClinicalFormRequest(
        @Valid @NotNull PatientComplaintDto complaint,
        @Valid @NotNull VitalSignsDto vitalSigns,
        @Valid @NotNull MedicalHistoryDto medicalHistory
) {
}

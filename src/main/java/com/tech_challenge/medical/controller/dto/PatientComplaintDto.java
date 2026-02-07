package com.tech_challenge.medical.controller.dto;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Patient complaint details")
public record PatientComplaintDto(
        @NotBlank @Schema(description = "Short description of complaint", example = "Severe abdominal pain") String description,
        @NotBlank @Schema(description = "Duration description", example = "2 days") String duration,
        @NotBlank @Schema(description = "Reported severity (e.g., mild, moderate, severe)", example = "severe") String severity
) {
}

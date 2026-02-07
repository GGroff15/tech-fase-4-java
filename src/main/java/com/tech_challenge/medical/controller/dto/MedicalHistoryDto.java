package com.tech_challenge.medical.controller.dto;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Patient medical history details")
public record MedicalHistoryDto(
        @Schema(description = "Known medical conditions") List<String> conditions,
        @Schema(description = "Current medications") List<String> medications,
        @Schema(description = "Known allergies") List<String> allergies
) {
}

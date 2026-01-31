package com.tech_challenge.medical.controller;

import com.tech_challenge.medical.application.TriageService;
import com.tech_challenge.medical.controller.dto.ClinicalFormRequest;
import com.tech_challenge.medical.controller.dto.TriageResultResponse;
import com.tech_challenge.medical.domain.form.*;
import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.triage.TriageResult;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/forms")
@Tag(name = "Forms", description = "Clinical form submission and processing endpoints")
public class FormController {
    private final TriageService triageService;

    public FormController(TriageService triageService) {
        this.triageService = triageService;
    }

    @PostMapping("/{correlationId}")
    @Operation(summary = "Submit clinical form", description = "Submit the clinical form to finalize the session and request a triage result")
    @ApiResponse(responseCode = "200", description = "Triage result returned")
    public ResponseEntity<TriageResultResponse> submitForm(
            @Parameter(description = "Correlation id for the session", required = true)
            @PathVariable String correlationId,
            @Valid @RequestBody ClinicalFormRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        ClinicalForm form = mapToForm(request);
        TriageResult result = triageService.processTriage(id, form);
        TriageResultResponse response = mapToResponse(result);
        
        return ResponseEntity.ok(response);
    }

    private ClinicalForm mapToForm(ClinicalFormRequest request) {
        PatientComplaint complaint = PatientComplaint.of(
                request.complaint().description(),
                request.complaint().duration(),
                request.complaint().severity()
        );

        VitalSigns vitalSigns = VitalSigns.of(
                request.vitalSigns().heartRate() != null 
                        ? HeartRate.of(request.vitalSigns().heartRate()) 
                        : null,
                request.vitalSigns().systolicBp() != null && request.vitalSigns().diastolicBp() != null
                        ? BloodPressure.of(request.vitalSigns().systolicBp(), request.vitalSigns().diastolicBp())
                        : null,
                request.vitalSigns().temperature() != null
                        ? Temperature.ofCelsius(request.vitalSigns().temperature())
                        : null,
                request.vitalSigns().oxygenSaturation() != null
                        ? OxygenSaturation.of(request.vitalSigns().oxygenSaturation())
                        : null,
                request.vitalSigns().respiratoryRate() != null
                        ? RespiratoryRate.of(request.vitalSigns().respiratoryRate())
                        : null
        );

        MedicalHistory medicalHistory = MedicalHistory.of(
                request.medicalHistory().conditions(),
                request.medicalHistory().medications(),
                request.medicalHistory().allergies()
        );

        return ClinicalForm.of(complaint, vitalSigns, medicalHistory);
    }

    private TriageResultResponse mapToResponse(TriageResult result) {
        return new TriageResultResponse(
                result.triageLevel().name(),
                result.riskFactors().asStringList(),
                result.inconsistencies().asStringList(),
                result.notesForPhysician().asString(),
                result.confidence().asDouble()
        );
    }
}

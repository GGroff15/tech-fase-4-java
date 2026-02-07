package com.tech_challenge.medical.domain.form;

import java.util.Map;
import java.util.Objects;

public final class ClinicalForm {
    private final PatientComplaint complaint;
    private final VitalSigns vitalSigns;
    private final MedicalHistory medicalHistory;

    private ClinicalForm(PatientComplaint complaint, VitalSigns vitalSigns, MedicalHistory medicalHistory) {
        this.complaint = Objects.requireNonNull(complaint, "Complaint cannot be null");
        this.vitalSigns = Objects.requireNonNull(vitalSigns, "Vital signs cannot be null");
        this.medicalHistory = Objects.requireNonNull(medicalHistory, "Medical history cannot be null");
    }

    public static ClinicalForm of(PatientComplaint complaint, VitalSigns vitalSigns, MedicalHistory medicalHistory) {
        return new ClinicalForm(complaint, vitalSigns, medicalHistory);
    }

    public PatientComplaint complaint() {
        return complaint;
    }

    public VitalSigns vitalSigns() {
        return vitalSigns;
    }

    public MedicalHistory medicalHistory() {
        return medicalHistory;
    }

    public Map<String, Object> asMap() {
        return Map.of(
                "complaint", complaint.asMap(),
                "vitalSigns", vitalSigns.asMap(),
                "medicalHistory", medicalHistory.asMap()
        );
    }
}

package com.tech_challenge.medical.domain.form;

import java.util.List;
import java.util.Map;

public final class MedicalHistory {
    private final List<String> conditions;
    private final List<String> medications;
    private final List<String> allergies;

    private MedicalHistory(List<String> conditions, List<String> medications, List<String> allergies) {
        this.conditions = conditions != null ? List.copyOf(conditions) : List.of();
        this.medications = medications != null ? List.copyOf(medications) : List.of();
        this.allergies = allergies != null ? List.copyOf(allergies) : List.of();
    }

    public static MedicalHistory of(List<String> conditions, List<String> medications, List<String> allergies) {
        return new MedicalHistory(conditions, medications, allergies);
    }

    public static MedicalHistory empty() {
        return new MedicalHistory(List.of(), List.of(), List.of());
    }

    public List<String> conditions() {
        return conditions;
    }

    public List<String> medications() {
        return medications;
    }

    public List<String> allergies() {
        return allergies;
    }

    public boolean hasConditions() {
        return !conditions.isEmpty();
    }

    public boolean hasMedications() {
        return !medications.isEmpty();
    }

    public boolean hasAllergies() {
        return !allergies.isEmpty();
    }

    public Map<String, List<String>> asMap() {
        return Map.of(
                "conditions", conditions,
                "medications", medications,
                "allergies", allergies
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        MedicalHistory that = (MedicalHistory) obj;
        return conditions.equals(that.conditions)
                && medications.equals(that.medications)
                && allergies.equals(that.allergies);
    }

    @Override
    public int hashCode() {
        int result = conditions.hashCode();
        result = 31 * result + medications.hashCode();
        result = 31 * result + allergies.hashCode();
        return result;
    }
}

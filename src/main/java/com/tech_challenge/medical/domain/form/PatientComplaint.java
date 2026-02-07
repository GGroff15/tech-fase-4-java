package com.tech_challenge.medical.domain.form;

import java.util.Map;

public final class PatientComplaint {
    private final String description;
    private final String duration;
    private final String severity;

    private PatientComplaint(String description, String duration, String severity) {
        this.description = validateNotBlank(description, "Description");
        this.duration = validateNotBlank(duration, "Duration");
        this.severity = validateNotBlank(severity, "Severity");
    }

    public static PatientComplaint of(String description, String duration, String severity) {
        return new PatientComplaint(description, duration, severity);
    }

    private String validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or blank");
        }
        return value.trim();
    }

    public String description() {
        return description;
    }

    public String duration() {
        return duration;
    }

    public String severity() {
        return severity;
    }

    public Map<String, String> asMap() {
        return Map.of(
                "description", description,
                "duration", duration,
                "severity", severity
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
        PatientComplaint that = (PatientComplaint) obj;
        return description.equals(that.description)
                && duration.equals(that.duration)
                && severity.equals(that.severity);
    }

    @Override
    public int hashCode() {
        int result = description.hashCode();
        result = 31 * result + duration.hashCode();
        result = 31 * result + severity.hashCode();
        return result;
    }
}

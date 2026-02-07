package com.tech_challenge.medical.domain.triage;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TriageResult {
    private final TriageLevel triageLevel;
    private final RiskFactors riskFactors;
    private final Inconsistencies inconsistencies;
    private final PhysicianNotes notesForPhysician;
    private final Confidence confidence;

    private TriageResult(
            TriageLevel triageLevel,
            RiskFactors riskFactors,
            Inconsistencies inconsistencies,
            PhysicianNotes notesForPhysician,
            Confidence confidence
    ) {
        this.triageLevel = Objects.requireNonNull(triageLevel, "Triage level cannot be null");
        this.riskFactors = Objects.requireNonNull(riskFactors, "Risk factors cannot be null");
        this.inconsistencies = Objects.requireNonNull(inconsistencies, "Inconsistencies cannot be null");
        this.notesForPhysician = Objects.requireNonNull(notesForPhysician, "Physician notes cannot be null");
        this.confidence = Objects.requireNonNull(confidence, "Confidence cannot be null");
    }

    public static TriageResult of(
            TriageLevel triageLevel,
            RiskFactors riskFactors,
            Inconsistencies inconsistencies,
            PhysicianNotes notesForPhysician,
            Confidence confidence
    ) {
        return new TriageResult(triageLevel, riskFactors, inconsistencies, notesForPhysician, confidence);
    }

    public TriageLevel triageLevel() {
        return triageLevel;
    }

    public RiskFactors riskFactors() {
        return riskFactors;
    }

    public Inconsistencies inconsistencies() {
        return inconsistencies;
    }

    public PhysicianNotes notesForPhysician() {
        return notesForPhysician;
    }

    public Confidence confidence() {
        return confidence;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("triageLevel", triageLevel.name());
        map.put("riskFactors", riskFactors.asStringList());
        map.put("inconsistencies", inconsistencies.asStringList());
        map.put("notesForPhysician", notesForPhysician.asString());
        map.put("confidence", confidence.asDouble());
        return map;
    }
}

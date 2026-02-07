package com.tech_challenge.medical.domain.summary;

import java.util.Map;

public final class TranscriptSummary {
    private final String consolidatedText;
    private final boolean hasLowConfidenceSegments;

    private TranscriptSummary(String consolidatedText, boolean hasLowConfidenceSegments) {
        if (consolidatedText == null) {
            throw new IllegalArgumentException("Consolidated text cannot be null");
        }
        this.consolidatedText = consolidatedText;
        this.hasLowConfidenceSegments = hasLowConfidenceSegments;
    }

    public static TranscriptSummary of(String consolidatedText, boolean hasLowConfidenceSegments) {
        return new TranscriptSummary(consolidatedText, hasLowConfidenceSegments);
    }

    public String consolidatedText() {
        return consolidatedText;
    }

    public boolean hasLowConfidenceSegments() {
        return hasLowConfidenceSegments;
    }

    public Map<String, Object> asMap() {
        return Map.of(
                "consolidatedText", consolidatedText,
                "hasLowConfidenceSegments", hasLowConfidenceSegments
        );
    }
}

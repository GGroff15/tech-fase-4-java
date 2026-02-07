package com.tech_challenge.medical.domain.reference;

/**
 * Status indicating how a vital sign value compares to its reference range.
 */
public enum RangeStatus {
    BELOW_NORMAL("Below Normal"),
    NORMAL("Normal"),
    ABOVE_NORMAL("Above Normal");

    private final String displayName;

    RangeStatus(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}

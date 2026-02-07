package com.tech_challenge.medical.domain.events;

public enum EmotionLabel {
    NEUTRAL("NEU"),
    HAPPY("HAP"),
    SAD("SAD"),
    ANGRY("ANG"),
    FEARFUL("FEA"),
    CALM("CAL"),
    DISGUSTED("DIS"),
    SURPRISED("SUR");

    private final String code;

    EmotionLabel(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static EmotionLabel fromCode(String code) {
        for (EmotionLabel emotion : EmotionLabel.values()) {
            if (emotion.code.equalsIgnoreCase(code)) {
                return emotion;
            }
        }
        throw new IllegalArgumentException("Unknown emotion code: " + code);
    }
}

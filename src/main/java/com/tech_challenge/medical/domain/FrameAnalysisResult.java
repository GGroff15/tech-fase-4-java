package com.tech_challenge.medical.domain;

import java.util.List;
import java.util.UUID;

public record FrameAnalysisResult(
        List<Detection> detections) {

    public boolean hasAnomalies() {
        return detections != null && !detections.isEmpty();
    }

}

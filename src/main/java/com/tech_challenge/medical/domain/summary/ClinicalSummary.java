package com.tech_challenge.medical.domain.summary;

import com.tech_challenge.medical.domain.form.ClinicalForm;
import com.tech_challenge.medical.domain.reference.EvaluatedVitalSigns;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ClinicalSummary {
    private final ClinicalForm formData;
    private final EvaluatedVitalSigns evaluatedVitalSigns;
    private final EmotionSummary emotionSummary;
    private final TranscriptSummary transcriptSummary;
    private final VideoSummary videoSummary;
    private final Instant sessionStart;
    private final Instant sessionEnd;

    private ClinicalSummary(
            ClinicalForm formData,
            EvaluatedVitalSigns evaluatedVitalSigns,
            EmotionSummary emotionSummary,
            TranscriptSummary transcriptSummary,
            VideoSummary videoSummary,
            Instant sessionStart,
            Instant sessionEnd
    ) {
        this.formData = Objects.requireNonNull(formData, "Form data cannot be null");
        this.evaluatedVitalSigns = Objects.requireNonNull(evaluatedVitalSigns, "Evaluated vital signs cannot be null");
        this.emotionSummary = Objects.requireNonNull(emotionSummary, "Emotion summary cannot be null");
        this.transcriptSummary = Objects.requireNonNull(transcriptSummary, "Transcript summary cannot be null");
        this.videoSummary = Objects.requireNonNull(videoSummary, "Video summary cannot be null");
        this.sessionStart = Objects.requireNonNull(sessionStart, "Session start cannot be null");
        this.sessionEnd = Objects.requireNonNull(sessionEnd, "Session end cannot be null");
        
        if (sessionEnd.isBefore(sessionStart)) {
            throw new IllegalArgumentException("Session end cannot be before session start");
        }
    }

    public static ClinicalSummary of(
            ClinicalForm formData,
            EvaluatedVitalSigns evaluatedVitalSigns,
            EmotionSummary emotionSummary,
            TranscriptSummary transcriptSummary,
            VideoSummary videoSummary,
            Instant sessionStart,
            Instant sessionEnd
    ) {
        return new ClinicalSummary(formData, evaluatedVitalSigns, emotionSummary, transcriptSummary, videoSummary, sessionStart, sessionEnd);
    }

    public ClinicalForm formData() {
        return formData;
    }

    public EvaluatedVitalSigns evaluatedVitalSigns() {
        return evaluatedVitalSigns;
    }

    public EmotionSummary emotionSummary() {
        return emotionSummary;
    }

    public TranscriptSummary transcriptSummary() {
        return transcriptSummary;
    }

    public VideoSummary videoSummary() {
        return videoSummary;
    }

    public Instant sessionStart() {
        return sessionStart;
    }

    public Instant sessionEnd() {
        return sessionEnd;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("formData", formData.asMap());
        map.put("evaluatedVitalSigns", evaluatedVitalSigns.asMap());
        map.put("emotionSummary", emotionSummary.asMap());
        map.put("transcriptSummary", transcriptSummary.asMap());
        map.put("videoSummary", videoSummary.asMap());
        map.put("sessionStart", sessionStart.toString());
        map.put("sessionEnd", sessionEnd.toString());
        return map;
    }
}

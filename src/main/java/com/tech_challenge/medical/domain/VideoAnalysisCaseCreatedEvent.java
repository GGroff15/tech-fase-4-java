package com.tech_challenge.medical.domain;

import com.tech_challenge.medical.application.UploadVideoService;
import org.springframework.context.ApplicationEvent;

public class VideoAnalysisCaseCreatedEvent extends ApplicationEvent {
    private final Long analysisCaseId;

    public VideoAnalysisCaseCreatedEvent(UploadVideoService uploadVideoService, Long analysisCaseId) {
        super(uploadVideoService);
        this.analysisCaseId = analysisCaseId;
    }

    public Long getAnalysisCaseId() {
        return analysisCaseId;
    }
}

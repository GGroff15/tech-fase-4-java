package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.form.ClinicalForm;
import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.session.SessionBuffer;
import com.tech_challenge.medical.domain.summary.ClinicalSummary;
import com.tech_challenge.medical.domain.triage.TriageResult;
import com.tech_challenge.medical.infrastructure.llm.LlmClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TriageService {
    private static final Logger log = LoggerFactory.getLogger(TriageService.class);

    private final BufferService bufferService;
    private final SummarizationService summarizationService;
    private final LlmClient llmClient;
    private final SessionService sessionService;

    public TriageService(
            BufferService bufferService,
            SummarizationService summarizationService,
            LlmClient llmClient,
            SessionService sessionService
    ) {
        this.bufferService = bufferService;
        this.summarizationService = summarizationService;
        this.llmClient = llmClient;
        this.sessionService = sessionService;
    }

    public TriageResult processTriage(CorrelationId correlationId, ClinicalForm form) {
        if (correlationId == null) {
            throw new IllegalArgumentException("Correlation ID cannot be null");
        }
        if (form == null) {
            throw new IllegalArgumentException("Clinical form cannot be null");
        }

        log.info("Processing triage for session: {}", correlationId.asString());
        
        SessionBuffer buffer = bufferService.findBuffer(correlationId);
        ClinicalSummary summary = summarizationService.summarize(buffer, form);
        TriageResult result = llmClient.analyzeClinicalSummary(summary);
        sessionService.closeSession(correlationId);
        
        log.info("Triage completed for session: {} - Level: {}", 
                correlationId.asString(), 
                result.triageLevel());
        
        return result;
    }
}

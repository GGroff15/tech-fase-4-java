package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.VideoAnalysisCaseCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
class ProcessVideoListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessVideoListener.class);

    @Async
    @EventListener
    void on(VideoAnalysisCaseCreatedEvent event) throws InterruptedException {
        LOGGER.info("Processing video for AnalysisCase ID: {}", event.getAnalysisCaseId());

        Thread.sleep(10000);

        LOGGER.info("Processed video for AnalysisCase ID: {}", event.getAnalysisCaseId());
    }

}

package com.tech_challenge.medical.application;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.Status;
import com.tech_challenge.medical.domain.VideoAnalysisCaseCreatedEvent;
import com.tech_challenge.medical.domain.VideoUpload;
import com.tech_challenge.medical.infraestructure.AnalysisCaseRepository;
import com.tech_challenge.medical.infraestructure.FileStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UploadVideoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadVideoService.class);

    private final FileStorage storage;
    private final AnalysisCaseRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public UploadVideoService(FileStorage storage, AnalysisCaseRepository repository, ApplicationEventPublisher eventPublisher) {
        this.storage = storage;
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public void execute(VideoUpload videoUpload) throws IOException {
        LOGGER.info("Uploading Video File");

        String rawFilePath = storage.store(videoUpload.file());

        AnalysisCase newAnalysisCase = new AnalysisCase();
        newAnalysisCase.setPatientId(videoUpload.patientId());
        newAnalysisCase.setType("VIDEO");
        newAnalysisCase.setStatus(Status.PENDING);
        newAnalysisCase.setRawFilePath(rawFilePath);
        newAnalysisCase.setSource(videoUpload.source());

        AnalysisCase saved = repository.save(newAnalysisCase);

        LOGGER.info("Video AnalysisCase created with ID: {}", saved.getId());

        LOGGER.info("Sending VideoAnalysisCaseCreatedEvent for AnalysisCase ID: {}", saved.getId());
        eventPublisher.publishEvent(new VideoAnalysisCaseCreatedEvent(this, saved.getId()));
    }
}

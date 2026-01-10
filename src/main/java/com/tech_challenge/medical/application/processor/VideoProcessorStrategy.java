package com.tech_challenge.medical.application.processor;

import com.tech_challenge.medical.domain.AnalysisCase;
import com.tech_challenge.medical.domain.AnalysisResult;
import com.tech_challenge.medical.domain.FrameAnalysisResult;
import com.tech_challenge.medical.domain.VideoFrame;
import com.tech_challenge.medical.infraestructure.VideoAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class VideoProcessorStrategy implements ProcessorStrategy {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoProcessorStrategy.class);

    private final VideoAnalyzer videoAnalyzer;

    public VideoProcessorStrategy(VideoAnalyzer videoAnalyzer) {
        this.videoAnalyzer = videoAnalyzer;
    }

    @Override
    public AnalysisResult process(AnalysisCase analysisCase) {
        LOGGER.info("Processing Video Processor Strategy");

        String rawFilePath = analysisCase.getRawFilePath();

        FrameAnalysisResult frameAnalysisResult = videoAnalyzer.analyze(new File(rawFilePath));

        // Here you would typically aggregate frameAnalysisResult into a comprehensive AnalysisResult

        return new AnalysisResult();
    }
}

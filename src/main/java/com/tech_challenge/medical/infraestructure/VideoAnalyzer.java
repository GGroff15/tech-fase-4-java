package com.tech_challenge.medical.infraestructure;

import com.tech_challenge.medical.domain.FrameAnalysisResult;
import com.tech_challenge.medical.infraestructure.dto.ProcessVideoRequest;
import com.tech_challenge.medical.infraestructure.dto.ProcessVideoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.LinkOption;

@Component
public class VideoAnalyzer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VideoAnalyzer.class);

    private final YoloV8Client client;
    private final int framesPerSecond;

    public VideoAnalyzer(YoloV8Client client, @Value("${video.analysis.frames-per-second:1}") int framesPerSecond) {
        this.client = client;
        this.framesPerSecond = framesPerSecond;
    }

    public FrameAnalysisResult analyze(File file) {
        LOGGER.info("Analyzing video file {}", file.getAbsolutePath());

        ProcessVideoRequest request = new ProcessVideoRequest(new FileSystemResource(file), framesPerSecond);
        ProcessVideoResponse response = client.analyzeFrame(request);
        return new FrameAnalysisResult(response.detections());
    }

}

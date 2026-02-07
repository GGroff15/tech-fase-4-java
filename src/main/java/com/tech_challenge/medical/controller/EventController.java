package com.tech_challenge.medical.controller;

import com.tech_challenge.medical.application.BufferService;
import com.tech_challenge.medical.controller.dto.EmotionEventRequest;
import com.tech_challenge.medical.controller.dto.ObjectDetectionRequest;
import com.tech_challenge.medical.controller.dto.TranscriptChunkRequest;
import com.tech_challenge.medical.domain.events.*;
import com.tech_challenge.medical.domain.session.CorrelationId;
import com.tech_challenge.medical.domain.triage.Confidence;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.Instant;

@RestController
@RequestMapping("/events")
@Tag(name = "Events", description = "Event ingestion endpoints (emotion, transcript, object detection)")
public class EventController {
    private final BufferService bufferService;

    public EventController(BufferService bufferService) {
        this.bufferService = bufferService;
    }

    @PostMapping("/emotion")
    @Operation(summary = "Receive emotion event", description = "Append an emotion event to the session buffer")
    @ApiResponse(responseCode = "202", description = "Event accepted")
    public ResponseEntity<Void> receiveEmotionEvent(
            @Parameter(name = "X-Correlation-Id", in = ParameterIn.HEADER, description = "Correlation id for the session", required = true)
            @RequestHeader("X-Correlation-Id") String correlationId,
            @Valid @RequestBody EmotionEventRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        
        EmotionEvent event = EmotionEvent.of(
                EmotionLabel.valueOf(request.emotion().toUpperCase()),
                Confidence.of(request.confidence()),
                Instant.parse(request.timestamp())
        );
        
        bufferService.appendEvent(id, event);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/transcript")
    @Operation(summary = "Receive transcript chunk", description = "Append a transcript chunk to the session buffer")
    @ApiResponse(responseCode = "202", description = "Event accepted")
    public ResponseEntity<Void> receiveTranscriptChunk(
            @Parameter(name = "X-Correlation-Id", in = ParameterIn.HEADER, description = "Correlation id for the session", required = true)
            @RequestHeader("X-Correlation-Id") String correlationId,
            @Valid @RequestBody TranscriptChunkRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        
        TranscriptChunk chunk = TranscriptChunk.of(
                request.text(),
                Confidence.of(request.confidence()),
                TimeWindow.of(
                        Instant.parse(request.startTime()),
                        Instant.parse(request.endTime())
                )
        );
        
        bufferService.appendEvent(id, chunk);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/object")
    @Operation(summary = "Receive object detection", description = "Append an object detection event to the session buffer")
    @ApiResponse(responseCode = "202", description = "Event accepted")
    public ResponseEntity<Void> receiveObjectDetection(
            @Parameter(name = "X-Correlation-Id", in = ParameterIn.HEADER, description = "Correlation id for the session", required = true)
            @RequestHeader("X-Correlation-Id") String correlationId,
            @Valid @RequestBody ObjectDetectionRequest request
    ) {
        CorrelationId id = CorrelationId.from(correlationId);
        
        ObjectDetectionEvent detection = ObjectDetectionEvent.of(
                ObjectLabel.of(request.label()),
                Confidence.of(request.confidence()),
                FrameIndex.of(request.frameIndex())
        );
        
        bufferService.appendEvent(id, detection);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}

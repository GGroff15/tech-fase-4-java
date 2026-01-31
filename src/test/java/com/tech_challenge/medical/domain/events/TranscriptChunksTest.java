package com.tech_challenge.medical.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.tech_challenge.medical.domain.triage.Confidence;

public class TranscriptChunksTest {

    private TranscriptChunks transcriptChunk;

    @BeforeEach
    void setup() {
        transcriptChunk = TranscriptChunks.empty();
    }

    static Stream<Arguments> textChunksProvider() {
        return Stream.of(
                Arguments.of(
                        List.of(
                                new TranscriptChunk("world", Confidence.of(0.95), TimeWindow.of(Instant.ofEpochMilli(2000), Instant.ofEpochMilli(3000))),
                                new TranscriptChunk("Hello", Confidence.of(0.9), TimeWindow.of(Instant.ofEpochMilli(0), Instant.ofEpochMilli(1000))),
                                new TranscriptChunk("from", Confidence.of(0.85), TimeWindow.of(Instant.ofEpochMilli(1000), Instant.ofEpochMilli(2000)))
                        ),
                        "Hello from world"
                ),
                Arguments.of(
                        List.of(
                                new TranscriptChunk("test", Confidence.of(0.8), TimeWindow.of(Instant.ofEpochMilli(1000), Instant.ofEpochMilli(2000)) ),
                                new TranscriptChunk("a", Confidence.of(0.9), TimeWindow.of(Instant.ofEpochMilli(0), Instant.ofEpochMilli(1000))),
                                new TranscriptChunk("case", Confidence.of(0.95), TimeWindow.of(Instant.ofEpochMilli(2000), Instant.ofEpochMilli(3000)))
                        ),
                        "a test case"
                ),
                Arguments.of(
                        List.of(
                                new TranscriptChunk("test", Confidence.of(0.8), TimeWindow.of(Instant.ofEpochMilli(1000), Instant.ofEpochMilli(2000)) ),
                                new TranscriptChunk("a test", Confidence.of(0.9), TimeWindow.of(Instant.ofEpochMilli(0), Instant.ofEpochMilli(1000))),
                                new TranscriptChunk("case", Confidence.of(0.95), TimeWindow.of(Instant.ofEpochMilli(2000), Instant.ofEpochMilli(3000)))
                        ),
                        "a test case"
                )
        );
    }

    @ParameterizedTest
    @MethodSource("textChunksProvider")
    @DisplayName("should consolidate text from multiple chunks in correct order")
    void testConsolidatedText(List<TranscriptChunk> chunks, String expectedText) {

        transcriptChunk = TranscriptChunks.of(chunks);

        String consolidatedText = transcriptChunk.consolidatedText();
        assertEquals(expectedText, consolidatedText);
    }

}

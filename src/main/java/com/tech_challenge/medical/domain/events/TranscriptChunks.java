package com.tech_challenge.medical.domain.events;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.tech_challenge.medical.domain.JoinRemovingOverlapCollector;

public final class TranscriptChunks {
    private final List<TranscriptChunk> chunks;

    private TranscriptChunks(List<TranscriptChunk> chunks) {
        this.chunks = new ArrayList<>(chunks);
    }

    public static TranscriptChunks empty() {
        return new TranscriptChunks(List.of());
    }

    public static TranscriptChunks of(List<TranscriptChunk> chunks) {
        if (chunks == null) {
            throw new IllegalArgumentException("Chunks list cannot be null");
        }
        return new TranscriptChunks(chunks);
    }

    public TranscriptChunks add(TranscriptChunk chunk) {
        if (chunk == null) {
            throw new IllegalArgumentException("Chunk cannot be null");
        }
        List<TranscriptChunk> newChunks = new ArrayList<>(this.chunks);
        newChunks.add(chunk);
        return new TranscriptChunks(newChunks);
    }

    public String consolidatedText() {
        if (chunks.isEmpty()) {
            return "";
        }

        return chunks.stream()
                .sorted(Comparator.comparing(chunk -> chunk.timeWindow().start()))
                .map(TranscriptChunk::text)
                .collect(new JoinRemovingOverlapCollector());
    }

    public boolean hasLowConfidenceSegments() {
        return chunks.stream()
                .anyMatch(chunk -> chunk.confidence().isLow());
    }

    public List<TranscriptChunk> lowConfidenceChunks() {
        return chunks.stream()
                .filter(chunk -> chunk.confidence().isLow())
                .sorted(Comparator.comparing(chunk -> chunk.timeWindow().start()))
                .toList();
    }

    public boolean isEmpty() {
        return chunks.isEmpty();
    }

    public int size() {
        return chunks.size();
    }

    public List<TranscriptChunk> toList() {
        return List.copyOf(chunks);
    }
}

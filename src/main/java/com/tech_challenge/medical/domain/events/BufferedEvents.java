package com.tech_challenge.medical.domain.events;

public final class BufferedEvents {
    private final EmotionEvents emotions;
    private final TranscriptChunks transcripts;
    private final DetectedObjects objects;

    private BufferedEvents(EmotionEvents emotions, TranscriptChunks transcripts, DetectedObjects objects) {
        if (emotions == null) {
            throw new IllegalArgumentException("Emotions cannot be null");
        }
        if (transcripts == null) {
            throw new IllegalArgumentException("Transcripts cannot be null");
        }
        if (objects == null) {
            throw new IllegalArgumentException("Objects cannot be null");
        }
        this.emotions = emotions;
        this.transcripts = transcripts;
        this.objects = objects;
    }

    public static BufferedEvents empty() {
        return new BufferedEvents(
                EmotionEvents.empty(),
                TranscriptChunks.empty(),
                DetectedObjects.empty()
        );
    }

    public static BufferedEvents of(EmotionEvents emotions, TranscriptChunks transcripts, DetectedObjects objects) {
        return new BufferedEvents(emotions, transcripts, objects);
    }

    public BufferedEvents addEmotion(EmotionEvent event) {
        return new BufferedEvents(emotions.add(event), transcripts, objects);
    }

    public BufferedEvents addTranscript(TranscriptChunk chunk) {
        return new BufferedEvents(emotions, transcripts.add(chunk), objects);
    }

    public BufferedEvents addObject(ObjectDetectionEvent detection) {
        return new BufferedEvents(emotions, transcripts, objects.add(detection));
    }

    public EmotionEvents emotions() {
        return emotions;
    }

    public TranscriptChunks transcripts() {
        return transcripts;
    }

    public DetectedObjects objects() {
        return objects;
    }

    public boolean isEmpty() {
        return emotions.isEmpty() && transcripts.isEmpty() && objects.isEmpty();
    }
}

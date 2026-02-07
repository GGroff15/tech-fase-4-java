package com.tech_challenge.medical.domain.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class EmotionLabelTest {

    @ParameterizedTest
    @EnumSource(EmotionLabel.class)
    public void testFromCode(EmotionLabel emotion) {

        EmotionLabel result = EmotionLabel.fromCode(emotion.getCode());

        assertEquals(emotion, result);
    }

}
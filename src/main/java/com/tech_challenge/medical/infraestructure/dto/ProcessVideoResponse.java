package com.tech_challenge.medical.infraestructure.dto;

import com.tech_challenge.medical.domain.Detection;

import java.util.List;

public record ProcessVideoResponse(List<Detection> detections) {
}

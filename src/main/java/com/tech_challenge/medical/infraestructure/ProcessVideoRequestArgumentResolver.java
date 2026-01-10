package com.tech_challenge.medical.infraestructure;

import com.tech_challenge.medical.infraestructure.dto.ProcessVideoRequest;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.MethodParameter;
import org.springframework.web.service.invoker.HttpRequestValues;
import org.springframework.web.service.invoker.HttpServiceArgumentResolver;

public class ProcessVideoRequestArgumentResolver implements HttpServiceArgumentResolver {
    @Override
    public boolean resolve(@Nullable Object argument, MethodParameter parameter, HttpRequestValues.Builder requestValues) {
        if (parameter.getParameterType().equals(ProcessVideoRequest.class)) {
            ProcessVideoRequest request = (ProcessVideoRequest) argument;
            requestValues.addRequestPart("video", request.video());
            requestValues.addRequestPart("frames_per_second", String.valueOf(request.framesPerSecond()));
            return true;
        }
        return false;
    }
}

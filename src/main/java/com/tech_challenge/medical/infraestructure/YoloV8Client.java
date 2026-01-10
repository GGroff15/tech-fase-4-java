package com.tech_challenge.medical.infraestructure;

import com.tech_challenge.medical.infraestructure.dto.ProcessVideoRequest;
import com.tech_challenge.medical.infraestructure.dto.ProcessVideoResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/analyze-frame")
public interface YoloV8Client {

    @PostExchange(contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    ProcessVideoResponse analyzeFrame(@ModelAttribute ProcessVideoRequest request);

}

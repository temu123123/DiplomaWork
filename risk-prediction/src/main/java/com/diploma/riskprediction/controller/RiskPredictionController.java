package com.diploma.riskprediction.controller;

import com.diploma.riskprediction.dto.response.RiskAssessmentResponse;
import com.diploma.riskprediction.service.RiskPredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/risks")
@RequiredArgsConstructor
public class RiskPredictionController {

    private final RiskPredictionService service;

    @GetMapping("/student/{studentId}")
    @ResponseStatus(HttpStatus.OK)
    public RiskAssessmentResponse getLatestRisk(@PathVariable UUID studentId) {
        return service.getLatestRisk(studentId);
    }

    @GetMapping("/student/{studentId}/history")
    @ResponseStatus(HttpStatus.OK)
    public List<RiskAssessmentResponse> getRiskHistory(@PathVariable UUID studentId) {
        return service.getRiskHistory(studentId);
    }

    @PostMapping("/student/{studentId}/calculate")
    @ResponseStatus(HttpStatus.CREATED)
    public RiskAssessmentResponse calculateRisk(@PathVariable UUID studentId) {
        return service.calculateRisk(studentId);
    }
}

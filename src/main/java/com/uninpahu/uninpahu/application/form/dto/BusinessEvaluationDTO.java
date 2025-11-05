package com.uninpahu.uninpahu.application.form.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BusinessEvaluationDTO
{
    @Min(1) @Max(5)
    private Integer experienceLevel;

    private String expectations;

    @Min(1) @Max(5)
    private Integer salesForecast;

    @Min(1) @Max(5)
    private Integer collaborationInterest;

    private String expectedGrowth;
    private String supportNeeds;
}

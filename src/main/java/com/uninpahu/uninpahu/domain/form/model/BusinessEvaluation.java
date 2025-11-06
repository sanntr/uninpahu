package com.uninpahu.uninpahu.domain.form.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BusinessEvaluation
{
    private Integer experienceLevel;
    private String expectations;
    private Integer salesForecast;
    private Integer collaborationInterest;
    private String expectedGrowth;
    private String supportNeeds;
}

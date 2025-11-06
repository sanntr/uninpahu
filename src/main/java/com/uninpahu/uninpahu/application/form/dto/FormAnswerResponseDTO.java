package com.uninpahu.uninpahu.application.form.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FormAnswerResponseDTO
{
    private String id;
    private PersonalDataDTO personalData;
    private BusinessInformationDTO businessInformation;
    private BusinessEvaluationDTO businessEvaluation;
    private Date registerDate;
}

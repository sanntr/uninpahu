package com.uninpahu.uninpahu.application.form.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class FormAnswerCreateDTO
{
    @Valid
    @NotNull
    private PersonalDataDTO personalData;

    @Valid
    @NotNull
    private BusinessInformationDTO businessInformation;

    @Valid
    @NotNull
    private BusinessEvaluationDTO businessEvaluation;
}

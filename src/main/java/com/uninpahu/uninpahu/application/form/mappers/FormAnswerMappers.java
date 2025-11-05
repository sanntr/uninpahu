package com.uninpahu.uninpahu.application.form.mappers;

import com.uninpahu.uninpahu.application.form.dto.*;
import com.uninpahu.uninpahu.domain.form.model.BusinessEvaluation;
import com.uninpahu.uninpahu.domain.form.model.BusinessInformation;
import com.uninpahu.uninpahu.domain.form.model.FormsAnswer;
import com.uninpahu.uninpahu.domain.form.model.PersonalData;
import com.uninpahu.uninpahu.domain.producto.model.Producto;

public class FormAnswerMappers
{
    private FormAnswerMappers(){}

    public static FormsAnswer mapToEntity(FormAnswerCreateDTO dto)
    {
        FormsAnswer entity = new FormsAnswer();
        entity.setPersonalData(new PersonalData(
                dto.getPersonalData().getFullName(),
                dto.getPersonalData().getEmail(),
                dto.getPersonalData().getPhone(),
                dto.getPersonalData().getCodeArea(),
                dto.getPersonalData().getCity(),
                dto.getPersonalData().getCountry()
        ));

        entity.setBusinessInformation(new BusinessInformation(
                        dto.getBusinessInformation().getName(),
                        dto.getBusinessInformation().getDescription(),
                        dto.getBusinessInformation().getCategories(),
                        dto.getBusinessInformation().getKeywords(),
                        dto.getBusinessInformation().getLinksToSocials(),
                        dto.getBusinessInformation().getProducts()
                                .stream()
                                .map(p ->
                                        new Producto(p.getNombre(), p.getDescripcion(), p.getIdNegocio(), p.getDescuento(),
                                                p.getPrecio(), p.getStock(), p.getCalificacion(), p.getActivo()
                                        )).toList()
                )
        );

        entity.setBusinessEvaluation(new BusinessEvaluation(
                dto.getBusinessEvaluation().getExperienceLevel(),
                dto.getBusinessEvaluation().getExpectations(),
                dto.getBusinessEvaluation().getSalesForecast(),
                dto.getBusinessEvaluation().getCollaborationInterest(),
                dto.getBusinessEvaluation().getExpectedGrowth(),
                dto.getBusinessEvaluation().getSupportNeeds()
        ));

        return entity;
    }

    public static FormAnswerResponseDTO mapToResponseDTO (FormsAnswer entity){
        if (entity == null) return null;

        return FormAnswerResponseDTO.builder()
                .id(entity.getId())
                .personalData(mapPersonalDataToDTO(entity.getPersonalData()))
                .businessInformation(mapBusinessInformationToDTO(entity.getBusinessInformation()))
                .businessEvaluation(mapBusinessEvaluationToDTO(entity.getBusinessEvaluation()))
                .registerDate(entity.getRegisterDate())
                .build();
    }

    public static PersonalDataDTO mapPersonalDataToDTO(PersonalData personalData){
        if (personalData == null) return null;

        return PersonalDataDTO.builder()
                .fullName(personalData.getFullName())
                .email(personalData.getEmail())
                .phone(personalData.getPhone())
                .codeArea(personalData.getCodeArea())
                .country(personalData.getCountry())
                .country(personalData.getCountry())
                .build();
    }

    public static BusinessInformationDTO mapBusinessInformationToDTO(BusinessInformation businessInformation){
        if (businessInformation == null) return null;

        return BusinessInformationDTO.builder()
                .name(businessInformation.getName())
                .description(businessInformation.getDescription())
                .categories(businessInformation.getCategories())
                .keywords(businessInformation.getKeywords())
                .linksToSocials(businessInformation.getLinksToSocials())
                .products(businessInformation.getProducts()
                        .stream()
                        .map(p ->
                                new Producto(p.getNombre(), p.getDescripcion(), p.getIdNegocio(), p.getDescuento(),
                                        p.getPrecio(), p.getStock(), p.getCalificacion(), p.getActivo()
                                )).toList()).build();
    }

    public static BusinessEvaluationDTO mapBusinessEvaluationToDTO(BusinessEvaluation businessEvaluation){
        if (businessEvaluation == null) return null;

        return BusinessEvaluationDTO.builder()
                .experienceLevel(businessEvaluation.getExperienceLevel())
                .expectations(businessEvaluation.getExpectations())
                .salesForecast(businessEvaluation.getSalesForecast())
                .collaborationInterest(businessEvaluation.getCollaborationInterest())
                .expectedGrowth(businessEvaluation.getExpectedGrowth())
                .supportNeeds(businessEvaluation.getSupportNeeds())
                .build();
    }
}

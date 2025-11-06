package com.uninpahu.uninpahu.domain.form.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "respuestas_formulario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormsAnswer
{
    @Id
    private String id;
    private PersonalData personalData;
    private BusinessInformation businessInformation;
    private BusinessEvaluation businessEvaluation;
    private Date registerDate = new Date();
}

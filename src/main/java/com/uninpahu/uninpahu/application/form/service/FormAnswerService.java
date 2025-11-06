package com.uninpahu.uninpahu.application.form.service;

import com.uninpahu.uninpahu.application.form.dto.*;
import com.uninpahu.uninpahu.application.form.mappers.FormAnswerMappers;
import com.uninpahu.uninpahu.domain.form.model.FormsAnswer;
import com.uninpahu.uninpahu.domain.form.repository.FormsAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FormAnswerService
{
    @Autowired
    private FormsAnswerRepository repository;

    public FormAnswerResponseDTO create(FormAnswerCreateDTO dto) {
        FormsAnswer entity = FormAnswerMappers.mapToEntity(dto);
        entity.setRegisterDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));

        FormsAnswer saved = repository.save(entity);
        return FormAnswerMappers.mapToResponseDTO(saved);
    }

    public FormsAnswer save(FormsAnswer form)
    {
        return repository.save(form);
    }

    public List<FormAnswerResponseDTO> findAll()
    {
        return repository.findAll().stream().map(FormAnswerMappers::mapToResponseDTO).toList();
    }

    public FormAnswerResponseDTO getById(String id)
    {
        FormsAnswer entity = repository.findById(id).orElseThrow(() -> new RuntimeException("No existe"));

        return FormAnswerMappers.mapToResponseDTO(entity);
    }
}

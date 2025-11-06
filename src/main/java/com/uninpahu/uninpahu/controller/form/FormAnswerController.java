package com.uninpahu.uninpahu.controller.form;

import com.uninpahu.uninpahu.application.form.dto.FormAnswerCreateDTO;
import com.uninpahu.uninpahu.application.form.dto.FormAnswerResponseDTO;
import com.uninpahu.uninpahu.application.form.service.FormAnswerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form")
public class FormAnswerController
{
    @Autowired
    private FormAnswerService service;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FormAnswerCreateDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping ResponseEntity<List<FormAnswerResponseDTO>> list(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/id")
    public ResponseEntity<?> find(@PathVariable String id){
        FormAnswerResponseDTO dto = service.getById(id);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }
}

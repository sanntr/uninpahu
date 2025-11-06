package com.uninpahu.uninpahu.domain.form.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class PersonalData
{
    private String fullName;
    private String email;
    private String codeArea;
    private String phone;
    private String city;
    private String country;
}

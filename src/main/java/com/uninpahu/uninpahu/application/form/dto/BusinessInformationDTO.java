package com.uninpahu.uninpahu.application.form.dto;

import com.uninpahu.uninpahu.domain.producto.model.Producto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BusinessInformationDTO
{
    @NotBlank
    private String name;

    private String description;

    @NotEmpty
    private List<String> categories;

    private List<String> keywords;
    private List<String> linksToSocials;

    @Valid
    private List<Producto> products;
}

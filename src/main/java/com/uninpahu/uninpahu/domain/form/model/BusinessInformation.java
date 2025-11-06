package com.uninpahu.uninpahu.domain.form.model;

import com.uninpahu.uninpahu.domain.producto.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
public class BusinessInformation
{
    private String name;
    private String description;
    private List<String> categories;
    private List<String> keywords;
    private List<String> linksToSocials;
    private List<Producto> products;
}

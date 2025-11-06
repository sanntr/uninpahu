package com.uninpahu.uninpahu.domain.cart.model;

import com.uninpahu.uninpahu.domain.producto.model.Producto;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;
import java.util.List;

@Document(collection = "carrito")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Cart
{
    @Id
    private String id;
    @Field("id_usuario")
    private int userId;
    @Field("productos")
    private List<CartProduct> productsOnCart;
    @Field("fechaActualizacion")
    private Instant updateDate;
}

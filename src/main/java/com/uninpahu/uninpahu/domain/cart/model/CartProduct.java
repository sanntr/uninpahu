package com.uninpahu.uninpahu.domain.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CartProduct
{
    @Field("id_producto")
    private int idProduct;
    @Field("nombre_producto")
    private String productName;
    @Field("imagen_producto")
    private String productImage;
    @Field("cantidad")
    private int quantity;
    @Field("valor")
    private Double value;
    @Field("id_negocio")
    private int idBusiness;
    @Field("nombre_negocio")
    private String businessName;
    @Field("descuento")
    private Double discount;
}

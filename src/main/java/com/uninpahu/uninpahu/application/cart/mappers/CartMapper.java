package com.uninpahu.uninpahu.application.cart.mappers;

import com.uninpahu.uninpahu.domain.cart.model.Cart;
import com.uninpahu.uninpahu.application.cart.dto.*;

import java.time.Instant;

public class CartMapper
{
    private CartMapper(){}

    public static CartResponseDTO toDTO (Cart entity)
    {
        return CartResponseDTO.builder()
                .cartId(entity.getId())
                .idUser(entity.getUserId())
                .products(entity.getProductsOnCart().stream().map(p ->
                        CartProductDTO.builder().productId(p.getIdProduct())
                        .quantity(p.getQuantity())
                        .build()
                ).toList()).
                updateTime(entity.getUpdateDate())
                .build();
    }
}

package com.uninpahu.uninpahu.application.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CartProductDTO
{
    private int productId;
    private int quantity;
}

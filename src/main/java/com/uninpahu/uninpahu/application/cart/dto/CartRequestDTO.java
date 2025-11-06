package com.uninpahu.uninpahu.application.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CartRequestDTO
{
    private int userId;
    private int productId;
    private int quantity;
}

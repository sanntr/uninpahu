package com.uninpahu.uninpahu.application.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CartResponseDTO
{
    private String cartId;
    private int idUser;
    private List<CartProductDTO> products;
    private Instant updateTime;
}

package com.uninpahu.uninpahu.controller.cart;

import com.uninpahu.uninpahu.application.cart.dto.CartRequestDTO;
import com.uninpahu.uninpahu.application.cart.dto.CartResponseDTO;
import com.uninpahu.uninpahu.application.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController
{
    private final CartService service;

    @PostMapping
    public CartResponseDTO saveOrUpdate(@RequestBody CartRequestDTO dto){
        return service.saveOrUpdate(dto);
    }

    @GetMapping("/{userId}")
    public CartResponseDTO getCart(@PathVariable int userId) {
        return service.getByUser(userId);
    }

    @DeleteMapping("/{userId}")
    public void DeleteCart(@PathVariable int userId){
        service.deleteByUser(userId);
    }

    @PatchMapping("/{userId}/items/{productId}")
    public CartResponseDTO removeItem(@PathVariable int userId, @PathVariable int productId, @RequestParam(defaultValue = "decrement") String action)
    {
        if ("decrement".equalsIgnoreCase(action))
            return service.decrementItem(userId, productId);
        else
            throw new IllegalArgumentException("accion no soportada" + action);
    }
}

package com.uninpahu.uninpahu.application.cart.service;

import com.uninpahu.uninpahu.application.cart.dto.CartRequestDTO;
import com.uninpahu.uninpahu.application.cart.dto.CartResponseDTO;
import com.uninpahu.uninpahu.application.cart.mappers.CartMapper;
import com.uninpahu.uninpahu.domain.cart.model.Cart;
import com.uninpahu.uninpahu.domain.cart.model.CartProduct;
import com.uninpahu.uninpahu.domain.cart.repository.CartRepository;
import com.uninpahu.uninpahu.domain.producto.model.Producto;
import com.uninpahu.uninpahu.domain.producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService
{
    private final CartRepository cartRepository;
    private final ProductoRepository productRepository;

    public CartResponseDTO getByUser(int userId){
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null){
            cart = Cart.builder()
                    .userId(userId)
                    .productsOnCart(new ArrayList<>())
                    .updateDate(Instant.now()).build();
            cartRepository.save(cart);
        }

        return CartMapper.toDTO(cart);
    }

    public CartResponseDTO saveOrUpdate(CartRequestDTO request)
    {
        log.info("POST /cart payload: {}", request);
        if (request.getQuantity() <= 0)
            throw new IllegalArgumentException("La cantidad debe ser mayor a 0");

        Cart cart = cartRepository.findByUserId(request.getUserId());
        log.info("Cart encontrado para userId {}: {}", request.getUserId(), cart != null ? cart.getId() : "NULL");

        if (cart == null){
            cart = Cart.builder()
                    .userId(request.getUserId())
                    .productsOnCart(new ArrayList<>())
                    .build();
        }

        Producto product = productRepository.findById((long) request.getProductId()).
                orElseThrow(() -> new RuntimeException("Producto no encontrado " + request.getProductId()));

        log.info("Producto OK: {} (precio={}, desc={})", product.getNombre(), product.getPrecio(), product.getDescuento());

        Optional<CartProduct> existingOpt = cart.getProductsOnCart().stream().filter(cp -> cp.getIdProduct()
                == product.getId().intValue()).findFirst();

        if (existingOpt.isPresent())
        {
            CartProduct existing = existingOpt.get();
            existing.setQuantity(existing.getQuantity() + request.getQuantity());
            existing.setValue(product.getPrecio().doubleValue());
            existing.setDiscount(product.getDescuento() == null ? null : product.getDescuento().doubleValue());
        }else {
            String businessName = null;
            Integer idBusiness = product.getIdNegocio().intValue(); //si se necesita el id del negocio toca traer el repository del negocio;

            CartProduct newItem = CartProduct.builder()
                    .idProduct(product.getId().intValue())
                    .productName(product.getNombre())
                    .productImage(null)
                    .quantity(request.getQuantity())
                    .value(product.getPrecio().doubleValue())
                    .idBusiness(idBusiness == null ? 0 : idBusiness)
                    .businessName(businessName)
                    .discount(product.getDescuento() == null ? null : product.getDescuento().doubleValue())
                    .build();

            cart.getProductsOnCart().add(newItem);
        }

        cart.setUpdateDate(Instant.now());
        Cart saved = cartRepository.save(cart);

        log.info("Cart guardado: id={}, items={}, update={}",
                saved.getId(), saved.getProductsOnCart().size(), saved.getUpdateDate());
        return CartMapper.toDTO(saved);
    }

    public void deleteByUser(int userId){
        Cart cart = cartRepository.findByUserId(userId);
        if (cart != null){
            cartRepository.delete(cart);
        }
    }

    public CartResponseDTO decrementItem(int userId, int productId){
        Cart cart = cartRepository.findByUserId(userId);

        if (cart == null){
            throw new IllegalArgumentException("El carrito no existe para el usuario");
        }

        List<CartProduct> items = cart.getProductsOnCart();
        if (items == null || items.isEmpty())
            throw new IllegalStateException("El carrito esta vacio");

        Optional<CartProduct> existingOpt = items.stream().filter(p -> p.getIdProduct() == productId).findFirst();

        if (existingOpt.isEmpty())
            throw new IllegalArgumentException("El producto " + productId + "no esta en el carrito");

        CartProduct existing = existingOpt.get();
        int newQty = existing.getQuantity() - 1;

        if (newQty > 0)
            existing.setQuantity(newQty);
        else
            items.remove(existing);

        if (items.isEmpty()){
            cartRepository.delete(cart);
            return CartResponseDTO.builder()
                    .cartId(null)
                    .idUser(userId)
                    .products(List.of())
                    .updateTime(Instant.now())
                    .build();
        }

        cart.setProductsOnCart(items);
        cart.setUpdateDate(Instant.now());
        Cart saved = cartRepository.save(cart);

        return CartMapper.toDTO(saved);
    }
}



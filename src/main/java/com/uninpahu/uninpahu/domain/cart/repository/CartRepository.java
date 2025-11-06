package com.uninpahu.uninpahu.domain.cart.repository;

import com.uninpahu.uninpahu.domain.cart.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String>
{
    Cart findByUserId(int userId);
}

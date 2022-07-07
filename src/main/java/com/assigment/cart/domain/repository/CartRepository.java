package com.assigment.cart.domain.repository;

import com.assigment.cart.domain.models.Cart;
import com.assigment.cart.domain.models.CartStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserUUIDAndStatus(String uuid, CartStatus status);
    Optional<Cart> findByCartUUID(String uuid);
}
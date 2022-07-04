package com.assigment.cart.domain.repository;

import com.assigment.cart.domain.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartRepository extends MongoRepository<Cart, String> {
    Optional<Cart> findByUserUUID(String uuid);
}
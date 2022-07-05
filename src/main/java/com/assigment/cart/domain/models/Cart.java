package com.assigment.cart.domain.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Data
@Document(collection = "carts")
public class Cart {
    @Id
    private String id;
    private String userUUID;
    private Map<String, Integer> booksUUID;
    private Float price;

    @Indexed(unique = true)
    private String cartUUID;

    public Cart(String userUUID, Map<String, Integer> booksUUID, String cartUUID, Float price) {
        this.userUUID = userUUID;
        this.booksUUID = booksUUID;
        this.cartUUID = cartUUID;
        this.price = price;
    }
}

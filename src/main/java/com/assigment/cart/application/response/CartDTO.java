package com.assigment.cart.application.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CartDTO {
    private String userUUID;
    private List<String> booksUUID;
    private Float price;
    private String cartUUID;
}

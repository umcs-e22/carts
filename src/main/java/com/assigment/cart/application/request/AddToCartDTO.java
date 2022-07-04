package com.assigment.cart.application.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AddToCartDTO {
    private List<String> booksUUID;
}

package com.assigment.cart.application.response;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Book {
    private String id;
    private String author;
    private String title;
    private Float price;
    private String bookUUID;
}

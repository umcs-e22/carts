package com.assigment.cart.application.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {
    private Integer count;
    private String author;
    private String title;
    private Float price;
    private String bookUUID;

    public BookDTO(Book book) {
        this.author = book.getAuthor();
        this.title = book.getTitle();
        this.price = book.getPrice();
        this.bookUUID = book.getBookUUID();
    }
}

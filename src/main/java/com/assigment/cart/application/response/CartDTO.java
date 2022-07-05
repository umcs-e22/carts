package com.assigment.cart.application.response;


import com.assigment.cart.domain.models.Cart;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class CartDTO {
    private String userUUID;
    private List<BookDTO> books;
    private Float price;
    private String cartUUID;

    public CartDTO(Cart cart){
        this.userUUID = cart.getUserUUID();
        this.price = cart.getPrice();
        this.cartUUID = cart.getCartUUID();
        this.books = new ArrayList<>();
    }

    public void addBook(BookDTO book){this.books.add(book);}

}

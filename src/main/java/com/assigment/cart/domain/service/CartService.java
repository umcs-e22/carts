package com.assigment.cart.domain.service;

import com.assigment.cart.application.request.AddToCartDTO;
import com.assigment.cart.application.response.Book;
import com.assigment.cart.application.response.BookDTO;
import com.assigment.cart.application.response.CartDTO;
import com.assigment.cart.domain.repository.CartRepository;
import com.assigment.cart.domain.models.Cart;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {

    protected final CartRepository cartRepository;

    public ResponseEntity<?> getByUserUUID(String uuid) {
        log.info("Getting book:<"+uuid+">");
        Optional<Cart> cart = cartRepository.findByUserUUID(uuid);
        if(cart.isPresent()){
            CartDTO response = new CartDTO(cart.get());
            RestTemplate restTemplate = new RestTemplate();
            String bookResourceUrl = "http://localhost:8989/v1/books/";
            for (String key : cart.get().getBooksUUID().keySet()) {
                Book b = restTemplate
                        .getForObject(bookResourceUrl + key, Book.class);
                assert b != null;
                BookDTO b1 = new BookDTO(b);
                b1.setCount(cart.get().getBooksUUID().get(key));
                response.addBook(b1);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new CartDTO(createCart(uuid)), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> addToCart(AddToCartDTO books, String userUUID) {
        Optional<Cart> cart = cartRepository.findByUserUUID(userUUID);
        RestTemplate restTemplate = new RestTemplate();
        String bookResourceUrl = "http://localhost:8989/v1/books/";
        if(cart.isPresent()){
            books.getBooksUUID().forEach((String book) -> {
                Book b = restTemplate
                        .getForObject(bookResourceUrl + book, Book.class);
                if(cart.get().getBooksUUID().containsKey(book)){
                    cart.get().getBooksUUID().put(book, cart.get().getBooksUUID().get(book)+1);
                }else{
                    cart.get().getBooksUUID().put(book, 1);
                }
                assert b != null;
                cart.get().setPrice(cart.get().getPrice()+b.getPrice());
            });
            cartRepository.save(cart.get());
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }else{
            Cart newCart = createCart(userUUID);
            books.getBooksUUID().forEach((String book) -> {
                Book b = restTemplate
                        .getForObject(bookResourceUrl + book, Book.class);
                newCart.getBooksUUID().put(book, 1);
                assert b != null;
                newCart.setPrice(newCart.getPrice()+b.getPrice());
            });
            cartRepository.save(newCart);
            return new ResponseEntity<>(newCart, HttpStatus.OK);
        }
    }

    public ResponseEntity<?> removeFromCart(String book, String userUUID) {
        Optional<Cart> cart = cartRepository.findByUserUUID(userUUID);
        RestTemplate restTemplate = new RestTemplate();
        String bookResourceUrl = "http://localhost:8989/v1/books/";
        Book b = restTemplate.getForObject(bookResourceUrl + book, Book.class);

        if(cart.isPresent()){
            int newCount = cart.get().getBooksUUID().get(book)-1;
            if(newCount==0){
                cart.get().getBooksUUID().remove(book);
            }else{
                cart.get().getBooksUUID().put(book, newCount);
            }

            assert b != null;
            cart.get().setPrice(cart.get().getPrice()-b.getPrice());

            cartRepository.save(cart.get());
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }else{
            return  new ResponseEntity<>(HttpStatus.SEE_OTHER);
        }
    }

    public ResponseEntity<?> removeOne(String uuid){
        cartRepository.findByUserUUID(uuid)
                .ifPresentOrElse(p-> {
                    log.info("Deleted: "+ uuid + " from cart repository");
                    cartRepository.delete(p)
                    ;}, ()->{log.info("Not deleted: "+uuid+" because do not exist already.");});

        return ResponseEntity.ok("");
    }

    private Cart createCart(String userUUID) {
        Cart cart = new Cart(userUUID, Collections.emptyMap(), UUID.randomUUID().toString(), 0.f);
        return cartRepository.save(cart);
    }
}

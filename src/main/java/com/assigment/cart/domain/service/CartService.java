package com.assigment.cart.domain.service;

import com.assigment.cart.application.request.AddToCartDTO;
import com.assigment.cart.domain.repository.CartRepository;
import com.assigment.cart.domain.models.Cart;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class CartService {

    protected final CartRepository cartRepository;

    public ResponseEntity<?> getByUserUUID(String uuid) {
        log.info("Getting person:<"+uuid+">");
        Optional<Cart> cart = cartRepository.findByUserUUID(uuid);
        if(cart.isPresent()){
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(createCart(uuid), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> addToCart(AddToCartDTO books, String userUUID) {
        Optional<Cart> cart = cartRepository.findByUserUUID(userUUID);
        if(cart.isPresent()){
            List<String> list = Stream.concat(books.getBooksUUID().stream(), cart.get().getBooksUUID().stream()).toList();
            cart.get().setBooksUUID(list);
            cartRepository.save(cart.get());
            return new ResponseEntity<>(cart, HttpStatus.OK);
        }else{
            Cart newCart = createCart(userUUID);
            newCart.setBooksUUID(books.getBooksUUID());
            cartRepository.save(newCart);
            return new ResponseEntity<>(newCart, HttpStatus.OK);
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
        Cart cart = new Cart(userUUID, Collections.emptyList(), UUID.randomUUID().toString(), 0.f);
        return cartRepository.save(cart);
    }
}

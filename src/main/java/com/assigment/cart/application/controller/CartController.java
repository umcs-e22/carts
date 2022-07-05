package com.assigment.cart.application.controller;

import com.assigment.cart.application.request.AddToCartDTO;
import com.assigment.cart.domain.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("v1/carts")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("user/{userUUID}")
    public ResponseEntity<?> getCartByUser(@RequestHeader("X-auth-user-id") String user, @RequestHeader("X-auth-user-roles") String roles, @PathVariable String userUUID){
        if(roles.contains("ROLE_ADMIN")){
            return cartService.getByUserUUID(userUUID);
        }else if(!user.equals(userUUID)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return cartService.getByUserUUID(user);
    }

    @PostMapping("/add")
    public ResponseEntity<?> getCartByUser(@RequestHeader("X-auth-user-id") String userUUID, @RequestBody AddToCartDTO books){
        return cartService.addToCart(books, userUUID);
    }

    @DeleteMapping("/remove/{bookUUID}")
    public ResponseEntity<?> removeFromCart(@RequestHeader("X-auth-user-id") String userUUID, @PathVariable String bookUUID){
        return cartService.removeFromCart(bookUUID, userUUID);
    }

    @DeleteMapping("/{userUUID}")
    public ResponseEntity<?> removeOne(@RequestHeader("X-auth-user-id") String user, @RequestHeader("X-auth-user-roles") String roles, @PathVariable String userUUID){
        if(roles.contains("ROLE_ADMIN")){
            return cartService.removeOne(userUUID);
        }else if(!user.equals(userUUID)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return cartService.removeOne(user);
    }
}

package com.grocerymart.grocerymartbackend.controller;

import com.grocerymart.grocerymartbackend.dto.CartDTO;
import com.grocerymart.grocerymartbackend.model.Cart;
import com.grocerymart.grocerymartbackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired private CartService cartService;

    @PostMapping("/save")
    public boolean saveCart(@RequestBody CartDTO cart) {
        return cartService.saveCart(cart);
    }
}

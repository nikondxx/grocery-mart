package com.grocerymart.grocerymartbackend.service;

import com.grocerymart.grocerymartbackend.dto.CartDTO;
import com.grocerymart.grocerymartbackend.dto.CartItemDTO;
import com.grocerymart.grocerymartbackend.model.Cart;
import com.grocerymart.grocerymartbackend.model.CartItem;
import com.grocerymart.grocerymartbackend.model.Product;
import com.grocerymart.grocerymartbackend.repository.CartItemRepository;
import com.grocerymart.grocerymartbackend.repository.CartRepository;
import com.grocerymart.grocerymartbackend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    @Autowired private CartRepository cartRepository;
    @Autowired private ProductRepository productRepository;
    @Autowired private CartItemRepository cartItemRepository;
    @Transactional
    public boolean saveCart(CartDTO cart) {
        Cart newCart = new Cart();
        newCart.setId(cart.getId());

        List< CartItemDTO> cartItems = cart.getCartItems();
        List<CartItem> newCartItems = new ArrayList<>();

        for (CartItemDTO cartItem : cartItems) {

            CartItem newCartItem = cartItemRepository.findById(cartItem.getId()).orElse(null);
            if (newCartItem == null) {
                newCartItem = new CartItem();
            }
            else {
                newCartItem.setId(cartItem.getId());
            }
            newCartItem.setName(cartItem.getName());
            newCartItem.setImage(cartItem.getImage());
            newCartItem.setPrice(cartItem.getPrice());
            newCartItem.setCategory(cartItem.getCategory());
            newCartItem.setDescription(cartItem.getDescription());
            newCartItem.setQuantity(cartItem.getQuantity());

            Product product = productRepository.findById(cartItem.getProduct().getId()).orElse(null);
            if (product == null) {
                throw new RuntimeException("Product not found!");
            }
            newCartItem.setProduct(product);
            newCartItem.setCart(newCart);
            newCartItems.add(newCartItem);
        }
        newCart.setCartItems(newCartItems);
        cartRepository.save(newCart);
        return true;
    }
}

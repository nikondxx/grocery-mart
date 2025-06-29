package com.grocerymart.grocerymartbackend.repository;

import com.grocerymart.grocerymartbackend.model.Cart;
import com.grocerymart.grocerymartbackend.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> deleteAllByCart(Cart cart);
}

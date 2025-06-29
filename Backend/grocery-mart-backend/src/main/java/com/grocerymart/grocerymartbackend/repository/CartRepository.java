package com.grocerymart.grocerymartbackend.repository;

import com.grocerymart.grocerymartbackend.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
}

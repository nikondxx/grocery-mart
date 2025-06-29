package com.grocerymart.grocerymartbackend.repository;

import com.grocerymart.grocerymartbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

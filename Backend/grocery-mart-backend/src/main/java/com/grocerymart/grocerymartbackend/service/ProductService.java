package com.grocerymart.grocerymartbackend.service;

import com.grocerymart.grocerymartbackend.model.Product;
import com.grocerymart.grocerymartbackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}

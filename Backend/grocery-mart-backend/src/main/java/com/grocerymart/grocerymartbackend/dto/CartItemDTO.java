package com.grocerymart.grocerymartbackend.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long id;
    private ProductDTO product;
    private String name;
    private double price;
    private String image;
    private String category;
    private String description;
    private int quantity;
}

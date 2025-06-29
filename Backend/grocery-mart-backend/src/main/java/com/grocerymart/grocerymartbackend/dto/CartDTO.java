package com.grocerymart.grocerymartbackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private List<CartItemDTO> cartItems;
}

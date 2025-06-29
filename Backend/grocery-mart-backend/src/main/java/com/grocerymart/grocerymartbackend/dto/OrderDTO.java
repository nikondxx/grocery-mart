package com.grocerymart.grocerymartbackend.dto;

import com.grocerymart.grocerymartbackend.model.DeliveryOption;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDTO {
    private List<CartItemDTO> items;
    private double totalPrice;
    private LocalDateTime createdAt;
    private String address;
    private DeliveryOption deliveryOption;
    private Long userId;
    private String discountCode;
    private Double discountAmount;
}

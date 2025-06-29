package com.grocerymart.grocerymartbackend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class GroceryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "groceryOrder", cascade = CascadeType.ALL)
    @JsonManagedReference("items")
    private List<OrderItem> items;

    private Double totalPrice;
    private String discountCode;
    private Double discountAmount;

    @ManyToOne
    @JoinColumn(name = "delivery_option_id")
    private DeliveryOption deliveryOption;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private GroceryUser user;

    private String address;

    private LocalDateTime createdAt;
}

package com.grocerymart.grocerymartbackend.service;

import com.grocerymart.grocerymartbackend.dto.CartItemDTO;
import com.grocerymart.grocerymartbackend.dto.OrderDTO;
import com.grocerymart.grocerymartbackend.model.*;
import com.grocerymart.grocerymartbackend.repository.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class GroceryOrderService {
    @Autowired
    private GroceryOrderRepository groceryOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryOptionRepository deliveryOptionRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Transactional
    public GroceryOrder save(OrderDTO orderDTO) {
        if (orderDTO.getAddress() != null) {
            String[] address = orderDTO.getAddress().split("#");
            for (String s : address) {
                if (s == null || s.isEmpty()) {
                    return null;
                }
            }
        }
        GroceryOrder groceryOrder = new GroceryOrder();
        List<OrderItem> orderItems = new ArrayList<>();
        GroceryUser user = userRepository.findById(orderDTO.getUserId()).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found!");
        }
        List<String> discountList = new ArrayList<>(Arrays.asList(user.getDiscounts().split(",")));
        if (discountList.contains(orderDTO.getDiscountCode())){
            discountList.remove(orderDTO.getDiscountCode());
            user.setDiscounts(String.join(",", discountList));
            user = userRepository.save(user);
        }
        groceryOrder.setUser(user);
        log.info("User found for saving order");
        groceryOrder.setAddress(orderDTO.getAddress());
        groceryOrder.setCreatedAt(orderDTO.getCreatedAt());
        DeliveryOption deliveryOption = deliveryOptionRepository.findById(orderDTO.getDeliveryOption().getId())
                .orElseThrow(() -> new RuntimeException("Delivery option not found"));
        groceryOrder.setDeliveryOption(deliveryOption);

        for (CartItemDTO cartItemDTO: orderDTO.getItems()) {
            OrderItem orderItem = new OrderItem();

            Product product = productRepository.findById(cartItemDTO.getProduct().getId()).orElse(null);
            if (product == null) {
                throw new RuntimeException("Product not found");
            }
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItemDTO.getQuantity());
            orderItem.setGroceryOrder(groceryOrder);
            orderItems.add(orderItem);
        }
        groceryOrder.setItems(orderItems);
        groceryOrder.setDiscountCode(orderDTO.getDiscountCode());
        groceryOrder.setDiscountAmount(orderDTO.getDiscountAmount());
        groceryOrder.setTotalPrice(orderDTO.getTotalPrice());
        cartItemRepository.deleteAllByCart(user.getCart());
        return groceryOrderRepository.save(groceryOrder);
    }


}

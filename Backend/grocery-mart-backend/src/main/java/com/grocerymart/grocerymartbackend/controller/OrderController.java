package com.grocerymart.grocerymartbackend.controller;

import com.grocerymart.grocerymartbackend.dto.OrderDTO;
import com.grocerymart.grocerymartbackend.model.GroceryOrder;
import com.grocerymart.grocerymartbackend.service.GroceryOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private GroceryOrderService groceryOrderService;

    @PostMapping("/save")
    public GroceryOrder save(@RequestBody OrderDTO order) {
        return groceryOrderService.save(order);
    }
}

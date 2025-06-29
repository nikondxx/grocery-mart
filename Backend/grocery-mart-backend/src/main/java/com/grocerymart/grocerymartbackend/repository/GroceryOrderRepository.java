package com.grocerymart.grocerymartbackend.repository;

import com.grocerymart.grocerymartbackend.model.GroceryOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroceryOrderRepository extends JpaRepository<GroceryOrder, Long> {
}

package com.grocerymart.grocerymartbackend.repository;

import com.grocerymart.grocerymartbackend.model.DeliveryOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryOptionRepository extends JpaRepository<DeliveryOption, Long> {
}

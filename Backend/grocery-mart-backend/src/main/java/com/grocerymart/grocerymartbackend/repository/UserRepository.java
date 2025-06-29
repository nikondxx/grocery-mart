package com.grocerymart.grocerymartbackend.repository;

import com.grocerymart.grocerymartbackend.model.GroceryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GroceryUser, Long> {

    GroceryUser findByEmail(String email);
}

package com.grocerymart.grocerymartbackend.service;

import com.grocerymart.grocerymartbackend.dto.LoginRequestDTO;
import com.grocerymart.grocerymartbackend.dto.UserRegisterDTO;
import com.grocerymart.grocerymartbackend.model.Cart;
import com.grocerymart.grocerymartbackend.model.GroceryUser;
import com.grocerymart.grocerymartbackend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public GroceryUser registerUser(UserRegisterDTO dto) throws Exception {
        GroceryUser user = new GroceryUser();
        user.setDiscounts("SAVE10,FIRST20,WELCOME15");
        GroceryUser savedUser = userRepository.findByEmail(dto.getEmail());
        if (savedUser != null) {
            throw new Exception("User already exists");
        }
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        Cart cart = new Cart();
        user.setCart(cart);

        return userRepository.save(user);
    }

    @Transactional
    public GroceryUser login(LoginRequestDTO loginRequestDTO) {
        GroceryUser user = userRepository.findByEmail(loginRequestDTO.getEmail());

        if (user == null || !user.getPassword().equals(loginRequestDTO.getPassword())) {
            return null;
        }

        return user;
    }

    @Transactional
    public boolean canApplyDiscount(Long userId, String discount) {
        GroceryUser user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        String[] codes = user.getDiscounts().split(",");
        if (codes.length == 0) {
            return false;
        }
        List<String> discountList = new ArrayList<>(Arrays.asList(codes));
        return discountList.contains(discount);
    }
}

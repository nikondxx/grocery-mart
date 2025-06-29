package com.grocerymart.grocerymartbackend.controller;

import com.grocerymart.grocerymartbackend.dto.LoginRequestDTO;
import com.grocerymart.grocerymartbackend.dto.UserRegisterDTO;
import com.grocerymart.grocerymartbackend.model.GroceryUser;
import com.grocerymart.grocerymartbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public GroceryUser register(@RequestBody UserRegisterDTO user) {
        try{
            return userService.registerUser(user);
        }
        catch(Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<GroceryUser> login(@RequestBody LoginRequestDTO loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().isBlank()) {
            return ResponseEntity.badRequest().body(null);
        }
        GroceryUser user = userService.login(loginRequest);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("can-apply-discount")
    public ResponseEntity<Boolean> applyDiscount(@RequestParam Long userId, @RequestParam String discount) {
        return ResponseEntity.ok(userService.canApplyDiscount(userId, discount));
    }
}

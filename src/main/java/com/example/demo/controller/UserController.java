package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> addUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            response.put("error", "Name is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        if (user.getSalary() == null || user.getSalary() < 0) {
            response.put("error", "Valid salary is required");
            return ResponseEntity.badRequest().body(response);
        }

        User savedUser = userService.addUser(user);
        response.put("message", "User added successfully");
        response.put("user", savedUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/getsalary")
    public ResponseEntity<Map<String, Object>> getNthHighestSalary(@RequestParam int n) {
        Map<String, Object> response = new HashMap<>();
        
        if (n < 1) {
            response.put("error", "n must be greater than 0");
            return ResponseEntity.badRequest().body(response);
        }

        Optional<Double> nthHighestSalary = userService.getNthHighestSalary(n);
        
        if (nthHighestSalary.isPresent()) {
            response.put("n", n);
            response.put("nthHighestSalary", nthHighestSalary.get());
            return ResponseEntity.ok(response);
        } else {
            response.put("error", "Not enough distinct salaries found for n = " + n);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}


package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Optional<Double> getNthHighestSalary(int n) {
        List<User> allUsers = userRepository.findAll();
        
        if (allUsers.isEmpty() || n < 1) {
            return Optional.empty();
        }

        List<Double> distinctSalaries = allUsers.stream()
                .map(User::getSalary)
                .filter(salary -> salary != null)
                .distinct()
                .sorted((a, b) -> Double.compare(b, a)) 
                .collect(Collectors.toList());

        if (n > distinctSalaries.size()) {
            return Optional.empty();
        }

        return Optional.of(distinctSalaries.get(n - 1));
    }
}



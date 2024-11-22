package com.forex.trading.controller;

import com.forex.trading.domain.User;
import com.forex.trading.dto.UserDTO;
import com.forex.trading.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // GET /api/users - Fetch all users
    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getName()))
                .collect(Collectors.toList());
    }

    // POST /api/users - Create a new user
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        if (userDTO.getName() == null || userDTO.getName().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        User user = new User(userDTO.getName());
        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = new UserDTO(savedUser.getId(), savedUser.getName());

        return ResponseEntity.ok(savedUserDTO);
    }
}
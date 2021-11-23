package com.ead.authuser.adapter.controllers;

import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {

        if(userService.getAllUsers().isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");


        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable UUID userId) {

        Optional<UserDTO> userDTOOptional = userService.getUser(userId);

        if (userDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        return ResponseEntity.status(HttpStatus.OK).body(userDTOOptional.get());

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        Optional<UserDTO> userDTOOptional = userService.delete(userId);

        if (userDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");

    }




}

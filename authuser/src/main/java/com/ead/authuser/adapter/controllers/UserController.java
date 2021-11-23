package com.ead.authuser.adapter.controllers;

import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.UserService;
import com.ead.authuser.application.services.excepitons.InvalidPasswordException;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

        if (userService.getAllUsers().isEmpty())
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

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId,
                                        @RequestBody @JsonView(UserAuthDTO.UserView.UserPut.class) UserAuthDTO userPut) {

        Optional<UserDTO> userDTOOptional = userService.updateUser(userId, userPut);

        if (userDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        return ResponseEntity.status(HttpStatus.OK).body(userDTOOptional.get());

    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable UUID userId,
                                            @RequestBody @JsonView(UserAuthDTO.UserView.PasswordPut.class) UserAuthDTO passwordPut) {

        try {
            Optional<UserDTO> userDTOOptional = userService.updatePassword(userId, passwordPut);

            if (userDTOOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

            return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully!");
        } catch (InvalidPasswordException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }

    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<?> updateImage(@PathVariable UUID userId,
                                         @RequestBody @JsonView(UserAuthDTO.UserView.ImagePut.class) UserAuthDTO imagePut) {

        Optional<UserDTO> userDTOOptional = userService.updateImage(userId, imagePut);

        if (userDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        return ResponseEntity.status(HttpStatus.OK).body("Image updated successfully!");

    }
}

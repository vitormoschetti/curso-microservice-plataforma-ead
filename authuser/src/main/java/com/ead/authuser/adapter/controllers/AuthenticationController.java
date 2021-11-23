package com.ead.authuser.adapter.controllers;

import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody
                                        @JsonView(UserAuthDTO.UserView.RegistrationPost.class)
                                                UserAuthDTO userAuthDTO) {

        if (userService.existsUsername(userAuthDTO.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        if (userService.existsEmail(userAuthDTO.getEmail()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");

        UserDTO userDTO = userService.create(userAuthDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

}

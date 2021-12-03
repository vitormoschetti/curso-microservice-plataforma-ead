package com.ead.authuser.adapter.controllers;

import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthenticationController {


    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody @Validated(UserAuthDTO.UserView.RegistrationPost.class)
                                        @JsonView(UserAuthDTO.UserView.RegistrationPost.class)
                                                UserAuthDTO userAuthDTO) {

        log.debug("Post user", userAuthDTO);

        if (userService.existsUsername(userAuthDTO.getUsername())) {
            log.warn("username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if (userService.existsEmail(userAuthDTO.getEmail())) {
            log.warn("username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        UserDTO userDTO = userService.create(userAuthDTO);
        log.debug("User save {}", userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

}

package com.ead.authuser.adapter.inbound.controller;

import com.ead.authuser.adapter.inbound.controller.dto.UserAuthDTO;
import com.ead.authuser.adapter.inbound.controller.dto.UserDTO;
import com.ead.authuser.application.ports.service.UserServicePort;
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


    private final UserServicePort userServicePort;

    public AuthenticationController(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody @Validated(UserAuthDTO.UserView.RegistrationPost.class)
                                        @JsonView(UserAuthDTO.UserView.RegistrationPost.class)
                                                UserAuthDTO userAuthDTO) {

        log.debug("Post user", userAuthDTO);

        if (userServicePort.existsUsername(userAuthDTO.getUsername())) {
            log.warn("username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        }
        if (userServicePort.existsEmail(userAuthDTO.getEmail())) {
            log.warn("username already exists");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        UserDTO userDTO = userServicePort.create(userAuthDTO);
        log.debug("User save {}", userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
    }

}

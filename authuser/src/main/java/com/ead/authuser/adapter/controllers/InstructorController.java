package com.ead.authuser.adapter.controllers;

import com.ead.authuser.application.model.InstructorDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.InstructorService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/instructors")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InstructorController {

    private final InstructorService instructorService;

    @PostMapping("/subscriptions")
    public ResponseEntity<?> saveSubscriptionInstructor(@RequestBody @Valid InstructorDTO instructorDTO) {

        Optional<UserDTO> userDTOOptional = instructorService.subscription(instructorDTO);

        if (userDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        return ResponseEntity.status(HttpStatus.OK).body(userDTOOptional.get());
    }
}

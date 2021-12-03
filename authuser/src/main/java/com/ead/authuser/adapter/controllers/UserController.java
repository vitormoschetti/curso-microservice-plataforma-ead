package com.ead.authuser.adapter.controllers;

import com.ead.authuser.adapter.specifications.SpecificationTemplate;
import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.UserService;
import com.ead.authuser.application.services.excepitons.InvalidPasswordException;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers(SpecificationTemplate.UserSpec spec,
                                         @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable) {


        Page<UserDTO> userDTOPage = userService.getAllUsers(spec, pageable);

        if (userDTOPage.isEmpty()) {
            log.info("Users not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users not found");
        }

        log.debug("Users: {}", userDTOPage.getContent());
        return ResponseEntity.status(HttpStatus.OK).body(userDTOPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable UUID userId) {

        Optional<UserDTO> userDTOOptional = userService.getUser(userId);

        if (userDTOOptional.isEmpty()) {
            log.info("Users not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        log.debug("User: {}", userDTOOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(userDTOOptional.get());

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        Optional<UserDTO> userDTOOptional = userService.delete(userId);

        if (userDTOOptional.isEmpty()) {
            log.info("User not found for delete");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        log.debug("User deleted successfully {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");

    }

    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable UUID userId,
                                        @RequestBody @Validated(UserAuthDTO.UserView.UserPut.class)
                                        @JsonView(UserAuthDTO.UserView.UserPut.class) UserAuthDTO userPut) {

        Optional<UserDTO> userDTOOptional = userService.updateUser(userId, userPut);

        log.debug("Parameters for delete user: id {}, body {}", userId, userPut);

        if (userDTOOptional.isEmpty()) {
            log.info("User not found for update");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        log.debug("User update successfully {}", userDTOOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(userDTOOptional.get());

    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable UUID userId,
                                            @RequestBody @Validated(UserAuthDTO.UserView.PasswordPut.class)
                                            @JsonView(UserAuthDTO.UserView.PasswordPut.class) UserAuthDTO passwordPut) {

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
                                         @RequestBody @Validated(UserAuthDTO.UserView.ImagePut.class)
                                         @JsonView(UserAuthDTO.UserView.ImagePut.class) UserAuthDTO imagePut) {

        Optional<UserDTO> userDTOOptional = userService.updateImage(userId, imagePut);

        if (userDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

        return ResponseEntity.status(HttpStatus.OK).body("Image updated successfully!");

    }
}

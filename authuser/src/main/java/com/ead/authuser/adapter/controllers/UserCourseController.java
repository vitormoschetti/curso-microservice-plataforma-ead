package com.ead.authuser.adapter.controllers;

import com.ead.authuser.adapter.repository.entity.UserCourseEntity;
import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.application.model.CourseDTO;
import com.ead.authuser.application.model.SubscriptionDTO;
import com.ead.authuser.application.services.UserCourseService;
import com.ead.authuser.application.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@AllArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private final UserCourseService userCourseService;
    private final UserService userService;

    @GetMapping("/{userId}/courses")
    public ResponseEntity<PageImpl<CourseDTO>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                   @PathVariable(value = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(userCourseService.getAllCoursesByUser(userId, pageable));
    }

    @PostMapping("/{userId}/courses/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                          @RequestBody @Valid SubscriptionDTO subscriptionDTO) {

        Optional<UserEntity> userEntityOptional = userService.getEntity(userId);

        if(userEntityOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        UserEntity userEntity = userEntityOptional.get();

        if(userCourseService.existsByUserAndCourseId(userEntity, subscriptionDTO.getCourseId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já matriculado no curso");

        userCourseService.save(userEntity, subscriptionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado");
    }


}

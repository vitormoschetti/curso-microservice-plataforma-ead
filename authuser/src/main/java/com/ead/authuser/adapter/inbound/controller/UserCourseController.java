package com.ead.authuser.adapter.inbound.controller;

import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import com.ead.authuser.adapter.inbound.controller.dto.CourseDTO;
import com.ead.authuser.adapter.inbound.controller.dto.SubscriptionDTO;
import com.ead.authuser.application.model.UserModel;
import com.ead.authuser.application.ports.service.UserCourseServicePort;
import com.ead.authuser.application.ports.service.UserServicePort;
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

    private final UserCourseServicePort userCourseServicePort;
    private final UserServicePort userServicePort;

    @GetMapping("/{userId}/courses")
    public ResponseEntity<PageImpl<CourseDTO>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                   @PathVariable(value = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(userCourseServicePort.getAllCoursesByUser(userId, pageable));
    }

    @PostMapping("/{userId}/courses/subscription")
    public ResponseEntity<String> saveSubscriptionUserInCourse(@PathVariable(value = "userId") UUID userId,
                                                          @RequestBody @Valid SubscriptionDTO subscriptionDTO) {

        Optional<UserModel> userModelOptional = userServicePort.getEntity(userId);

        if(userModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");

        UserModel userModel = userModelOptional.get();

        if(userCourseServicePort.existsByUserAndCourseId(userModel, subscriptionDTO.getCourseId()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já matriculado no curso");

        userCourseServicePort.save(userModel, subscriptionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Usuário cadastrado");
    }


}

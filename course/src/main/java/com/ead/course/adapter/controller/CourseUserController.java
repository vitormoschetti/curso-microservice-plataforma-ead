package com.ead.course.adapter.controller;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.application.model.SubscriptionDTO;
import com.ead.course.application.model.UserDTO;
import com.ead.course.application.model.enums.UserStatus;
import com.ead.course.application.services.CourseService;
import com.ead.course.application.services.CourseUserService;
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
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final CourseUserService courseUserService;
    private final CourseService courseService;

    @GetMapping("/{courseId}/users")
    public ResponseEntity<PageImpl<UserDTO>> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                 @PathVariable(value = "courseId") UUID courseId) {

        return ResponseEntity.status(HttpStatus.OK).body(courseUserService.getAllUsersByCourse(courseId, pageable));
    }


    @PostMapping("/{courseId}/users/subscription")
    public ResponseEntity<?> saveSubscriptionUserInCourse(@PathVariable(value = "courseId") UUID courseId,
                                                          @RequestBody @Valid SubscriptionDTO subscriptionDTO) {

        Optional<CourseEntity> courseEntityOptional = courseService.getEntity(courseId);
        if (courseEntityOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        CourseEntity courseEntity = courseEntityOptional.get();
        if (courseUserService.existsByCourseAndUserId(courseEntity, subscriptionDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Subscription already exists");
        }

        UserDTO userDTO = courseUserService.getOneUserByUserId(subscriptionDTO.getUserId());

        log.info("User: {}", userDTO);

        if(userDTO.getUserStatus().equals(UserStatus.BLOCKED))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Usuário bloqueado");

        courseUserService.saveAndSendSubscriptionUserInCourse(courseEntity, subscriptionDTO.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully");
    }

}

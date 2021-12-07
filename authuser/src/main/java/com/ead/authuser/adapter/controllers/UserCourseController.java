package com.ead.authuser.adapter.controllers;

import com.ead.authuser.application.model.CourseDTO;
import com.ead.authuser.application.services.UserCourseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    private final UserCourseService userCourseService;

    public UserCourseController(UserCourseService userCourseService) {
        this.userCourseService = userCourseService;
    }

    @GetMapping("/{userId}/courses")
    public ResponseEntity<PageImpl<CourseDTO>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                   @PathVariable(value = "userId") UUID userId) {

        return ResponseEntity.status(HttpStatus.OK).body(userCourseService.getAllCoursesByUser(userId, pageable));
    }

}

package com.ead.course.adapter.controller;

import com.ead.course.application.model.UserDTO;
import com.ead.course.application.services.CourseUserService;
import lombok.extern.log4j.Log4j2;
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
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseUserController {

    private final CourseUserService courseUserService;

    public CourseUserController(CourseUserService courseUserService) {
        this.courseUserService = courseUserService;
    }

    @GetMapping("/{courseId}/users")
    public ResponseEntity<PageImpl<UserDTO>> getAllUsersByCourse(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                 @PathVariable(value = "courseId") UUID courseId) {

        return ResponseEntity.status(HttpStatus.OK).body(courseUserService.getAllUsersByCourse(courseId, pageable));
    }

}

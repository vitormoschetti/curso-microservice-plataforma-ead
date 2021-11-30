package com.ead.course.adapters.controllers;

import com.ead.course.application.model.CourseDTO;
import com.ead.course.application.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/course")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Validated CourseDTO courseDTO) {
        courseService.create(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDTO);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseDTO> courseDTOOptional = courseService.delete(courseId);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully");
    }

    @PutMapping("/{courseId")
    public ResponseEntity<?> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                          @RequestBody @Validated CourseDTO courseDTO) {

        Optional<CourseDTO> courseDTOOptional = courseService.update(courseId, courseDTO);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.status(HttpStatus.OK).body(courseDTOOptional.get());

    }

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getAll());
    }

    @GetMapping("/courseId")
    public ResponseEntity<?> getAllCourses(@PathVariable UUID courseId) {

        Optional<CourseDTO> courseDTOOptional = courseService.get(courseId);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.status(HttpStatus.OK).body(courseDTOOptional.get());

    }

}

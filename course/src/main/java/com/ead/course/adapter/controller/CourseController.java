package com.ead.course.adapter.controller;

import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.adapter.validation.CourseValidator;
import com.ead.course.application.model.CourseDTO;
import com.ead.course.application.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {

    private final CourseService courseService;
    private final CourseValidator courseValidator;

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody CourseDTO courseDTO, Errors errors) {

        courseValidator.validate(courseDTO, errors);

        if(errors.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());

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

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable(value = "courseId") UUID courseId,
                                          @RequestBody @Validated CourseDTO courseDTO) {

        Optional<CourseDTO> courseDTOOptional = courseService.update(courseId, courseDTO);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.status(HttpStatus.OK).body(courseDTOOptional.get());

    }

    @GetMapping
    public ResponseEntity<PageImpl<CourseDTO>> getAllCourses(SpecificationTemplate.CourseSpec spec,
                                                             @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @RequestParam(required = false) UUID userId) {

        if(userId == null)
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getAll(spec, pageable));
        else
            return ResponseEntity.status(HttpStatus.OK).body(courseService.getAll(SpecificationTemplate.courseUserId(userId).and(spec), pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> getCourses(@PathVariable UUID courseId) {

        Optional<CourseDTO> courseDTOOptional = courseService.get(courseId);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.status(HttpStatus.OK).body(courseDTOOptional.get());

    }

}

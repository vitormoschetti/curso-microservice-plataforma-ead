package com.ead.course.adapter.controller;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.application.model.ModuleDTO;
import com.ead.course.application.services.CourseService;
import com.ead.course.application.services.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/courses")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;


    @PostMapping("/{courseId}/modules")
    public ResponseEntity<?> create(@PathVariable UUID courseId,
                                    @RequestBody @Validated ModuleDTO moduleDTO) {

        final Optional<CourseEntity> courseEntityOptional = courseService.getEntity(courseId);

        if (courseEntityOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        moduleService.create(moduleDTO, courseEntityOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleDTO);
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<String> delete(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId) {

        Optional<ModuleDTO> moduleDTOOptional = moduleService.delete(courseId, moduleId);

        if (moduleDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found for course");

        return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully");
    }

    @PutMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> update(@PathVariable(value = "courseId") UUID courseId,
                                    @PathVariable(value = "moduleId") UUID moduleId,
                                    @RequestBody @Validated ModuleDTO moduleDTO) {

        Optional<ModuleDTO> moduleDTOOptional = moduleService.update(courseId, moduleId, moduleDTO);

        if (moduleDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course not found");

        return ResponseEntity.status(HttpStatus.OK).body(moduleDTOOptional.get());

    }

    @GetMapping("/{courseId}/modules")
    public ResponseEntity<PageImpl<ModuleDTO>> getAll(@PathVariable UUID courseId,
                                                      SpecificationTemplate.ModuleSpec spec,
                                                      @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleService.getAll(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> get(@PathVariable UUID courseId,
                                 @PathVariable UUID moduleId) {

        Optional<ModuleDTO> moduleDTOOptional = moduleService.get(courseId, moduleId);

        if (moduleDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found");

        return ResponseEntity.status(HttpStatus.OK).body(moduleDTOOptional.get());

    }

}

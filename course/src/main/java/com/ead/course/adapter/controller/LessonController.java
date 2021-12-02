package com.ead.course.adapter.controller;

import com.ead.course.adapter.repository.entity.ModuleEntity;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.application.model.LessonDTO;
import com.ead.course.application.services.LessonService;
import com.ead.course.application.services.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    private final ModuleService moduleService;
    private final LessonService lessonService;


    @PostMapping("/{moduleId}/lessons")
    public ResponseEntity<?> create(@PathVariable UUID moduleId,
                                    @RequestBody @Validated LessonDTO lessonDTO) {

        final Optional<ModuleEntity> moduleEntityOptional = moduleService.getEntity(moduleId);

        if (moduleEntityOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Module not found");

        lessonService.create(lessonDTO, moduleEntityOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonDTO);
    }

    @DeleteMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<String> delete(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId) {

        Optional<LessonDTO> lessonDTOOptional = lessonService.delete(moduleId, lessonId);

        if (lessonDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found for module");

        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully");
    }

    @PutMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> update(@PathVariable(value = "moduleId") UUID moduleId,
                                    @PathVariable(value = "lessonId") UUID lessonId,
                                    @RequestBody @Validated LessonDTO lessonDTO) {

        Optional<LessonDTO> lessonDTOOptional = lessonService.update(moduleId, lessonId, lessonDTO);

        if (lessonDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");

        return ResponseEntity.status(HttpStatus.OK).body(lessonDTOOptional.get());

    }

    @GetMapping("/{moduleId}/lessons")
    public ResponseEntity<List<LessonDTO>> getAll(@PathVariable UUID moduleId,
                                                  SpecificationTemplate.LessonSpec spec,
                                                  @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonService.getAll(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
    }

    @GetMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> get(@PathVariable UUID moduleId,
                                 @PathVariable UUID lessonId) {

        Optional<LessonDTO> lessonDTOOptional = lessonService.get(moduleId, lessonId);

        if (lessonDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found");

        return ResponseEntity.status(HttpStatus.OK).body(lessonDTOOptional.get());

    }

}

package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.inbound.controller.dto.validation.CursoValidator;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.adapter.inbound.controller.dto.CourseDTO;
import com.ead.course.application.ports.service.CourseServicePort;
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

    private final CourseServicePort courseServicePort;
    private final CursoValidator courseValidator;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody CourseDTO courseDTO, Errors errors) {

        courseValidator.validate(courseDTO, errors);

        if(errors.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.getAllErrors());

        courseServicePort.criar(courseDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDTO);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deletar(@PathVariable(value = "courseId") UUID courseId) {

        Optional<CourseDTO> courseDTOOptional = courseServicePort.deletar(courseId);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");

        return ResponseEntity.status(HttpStatus.OK).body("Curso deletado com sucesso!");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> atualizar(@PathVariable(value = "courseId") UUID courseId,
                                          @RequestBody @Validated CourseDTO courseDTO) {

        Optional<CourseDTO> courseDTOOptional = courseServicePort.atualizar(courseId, courseDTO);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");

        return ResponseEntity.status(HttpStatus.OK).body(courseDTOOptional.get());

    }

    @GetMapping
    public ResponseEntity<PageImpl<CourseDTO>> buscarTodos(SpecificationTemplate.CursoSpec spec,
                                                           @PageableDefault(page = 0, size = 10, sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
                                                           @RequestParam(required = false) UUID userId) {

        if(userId == null)
            return ResponseEntity.status(HttpStatus.OK).body(courseServicePort.buscarTodos(spec, pageable));
        else
            return ResponseEntity.status(HttpStatus.OK).body(courseServicePort.buscarTodos(SpecificationTemplate.courseUserId(userId).and(spec), pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<?> buscar(@PathVariable UUID courseId) {

        Optional<CourseDTO> courseDTOOptional = courseServicePort.buscar(courseId);

        if (courseDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");

        return ResponseEntity.status(HttpStatus.OK).body(courseDTOOptional.get());

    }

}

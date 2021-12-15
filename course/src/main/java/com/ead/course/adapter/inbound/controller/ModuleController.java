package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.adapter.inbound.controller.dto.ModuleDTO;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.ports.service.CourseServicePort;
import com.ead.course.application.ports.service.ModuleServicePort;
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

    private final ModuleServicePort moduleServicePort;
    private final CourseServicePort courseServicePort;


    @PostMapping("/{courseId}/modules")
    public ResponseEntity<?> criar(@PathVariable UUID courseId,
                                    @RequestBody @Validated ModuleDTO moduleDTO) {

        final Optional<CourseModel> cursoModelOptional = courseServicePort.buscarEntidade(courseId);

        if (cursoModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");

        moduleServicePort.create(moduleDTO, cursoModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(moduleDTO);
    }

    @DeleteMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<String> deletar(@PathVariable(value = "courseId") UUID courseId, @PathVariable(value = "moduleId") UUID moduleId) {

        Optional<ModuleDTO> moduleDTOOptional = moduleServicePort.delete(courseId, moduleId);

        if (moduleDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Módulo não encontrado para o curso");

        return ResponseEntity.status(HttpStatus.OK).body("Módulo deletado com sucesso");
    }

    @PutMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> atualizar(@PathVariable(value = "courseId") UUID courseId,
                                    @PathVariable(value = "moduleId") UUID moduleId,
                                    @RequestBody @Validated ModuleDTO moduleDTO) {

        Optional<ModuleDTO> moduleDTOOptional = moduleServicePort.update(courseId, moduleId, moduleDTO);

        if (moduleDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");

        return ResponseEntity.status(HttpStatus.OK).body(moduleDTOOptional.get());

    }

    @GetMapping("/{courseId}/modules")
    public ResponseEntity<PageImpl<ModuleDTO>> buscarTodos(@PathVariable UUID courseId,
                                                           SpecificationTemplate.ModuloSpec spec,
                                                           @PageableDefault(page = 0, size = 10, sort = "moduleId", direction = Sort.Direction.ASC) Pageable pageable) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(moduleServicePort.getAll(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable));
    }

    @GetMapping("/{courseId}/modules/{moduleId}")
    public ResponseEntity<?> buscar(@PathVariable UUID courseId,
                                 @PathVariable UUID moduleId) {

        Optional<ModuleDTO> moduleDTOOptional = moduleServicePort.get(courseId, moduleId);

        if (moduleDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Módulo não encontrado");

        return ResponseEntity.status(HttpStatus.OK).body(moduleDTOOptional.get());

    }

}

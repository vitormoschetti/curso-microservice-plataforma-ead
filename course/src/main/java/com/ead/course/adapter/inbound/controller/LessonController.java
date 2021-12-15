package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.inbound.controller.dto.LessonDTO;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.application.model.ModuleModel;
import com.ead.course.application.ports.service.LessonServicePort;
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
@RequestMapping("/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LessonController {

    private final ModuleServicePort moduleServicePort;
    private final LessonServicePort lessonServicePort;

    @PostMapping("/{moduleId}/lessons")
    public ResponseEntity<?> criar(@PathVariable UUID moduleId,
                                   @RequestBody @Validated LessonDTO lessonDTO) {

        final Optional<ModuleModel> moduloModelOptional = moduleServicePort.getEntity(moduleId);

        if (moduloModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Módulo não encontrado");

        lessonServicePort.criar(lessonDTO, moduloModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(lessonDTO);
    }

    @DeleteMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<String> deletar(@PathVariable(value = "moduleId") UUID moduleId, @PathVariable(value = "lessonId") UUID lessonId) {

        Optional<LessonDTO> lessonDTOOptional = lessonServicePort.deletar(moduleId, lessonId);

        if (lessonDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aula não encontrada no módulo");

        return ResponseEntity.status(HttpStatus.OK).body("Aula deletada com sucesso");
    }

    @PutMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> atualizar(@PathVariable(value = "moduleId") UUID moduleId,
                                       @PathVariable(value = "lessonId") UUID lessonId,
                                       @RequestBody @Validated LessonDTO lessonDTO) {

        Optional<LessonDTO> lessonDTOOptional = lessonServicePort.atualizar(moduleId, lessonId, lessonDTO);

        if (lessonDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aula não encontrada");

        return ResponseEntity.status(HttpStatus.OK).body(lessonDTOOptional.get());

    }

    @GetMapping("/{moduleId}/lessons")
    public ResponseEntity<PageImpl<LessonDTO>> getAll(@PathVariable UUID moduleId,
                                                      SpecificationTemplate.AulaSpec spec,
                                                      @PageableDefault(page = 0, size = 10, sort = "lessonId", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(lessonServicePort.buscarTodos(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
    }

    @GetMapping("/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<?> get(@PathVariable UUID moduleId,
                                 @PathVariable UUID lessonId) {

        Optional<LessonDTO> lessonDTOOptional = lessonServicePort.buscar(moduleId, lessonId);

        if (lessonDTOOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aula não encontrada");

        return ResponseEntity.status(HttpStatus.OK).body(lessonDTOOptional.get());

    }

}

package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.inbound.controller.dto.SubscriptionDTO;
import com.ead.course.adapter.inbound.controller.dto.UserDTO;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.enums.UserStatus;
import com.ead.course.application.ports.service.CourseServicePort;
import com.ead.course.application.useCase.CourseUserUseCase;
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

    private final CourseUserUseCase courseUserUseCase;
    private final CourseServicePort courseServicePort;

    @GetMapping("/{cursoId}/users")
    public ResponseEntity<PageImpl<UserDTO>> buscarTodosUsuariosNoCurso(@PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC) Pageable pageable,
                                                                        @PathVariable(value = "cursoId") UUID cursoId) {

        return ResponseEntity.status(HttpStatus.OK).body(courseUserUseCase.getAllUsersByCourse(cursoId, pageable));
    }


    @PostMapping("/{cursoId}/users/subscription")
    public ResponseEntity<?> salvarInscricaoDoUsuarioNoCurso(@PathVariable(value = "cursoId") UUID cursoId,
                                                             @RequestBody @Valid SubscriptionDTO subscriptionDTO) {

        Optional<CourseModel> cursoModelOptional = courseServicePort.buscarEntidade(cursoId);
        if (cursoModelOptional.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso não encontrado");

        CourseModel courseModel = cursoModelOptional.get();
        if (courseUserUseCase.existsByCourseAndUserId(courseModel, subscriptionDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Inscrição já existe");
        }

        UserDTO userDTO = courseUserUseCase.getOneUserByUserId(subscriptionDTO.getUserId());

        log.info("User: {}", userDTO);

        if(userDTO.getUserStatus().equals(UserStatus.BLOCKED))
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário bloqueado");

        courseUserUseCase.saveAndSendSubscriptionUserInCourse(courseModel, subscriptionDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body("Inscrição criada com sucesso");
    }

}

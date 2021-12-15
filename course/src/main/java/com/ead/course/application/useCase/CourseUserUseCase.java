package com.ead.course.application.useCase;

import com.ead.course.adapter.outbound.clients.AuthUserClient;
import com.ead.course.adapter.inbound.controller.dto.UserDTO;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.CourseUserModel;
import com.ead.course.application.ports.repository.CourseUserRepositoryPort;
import com.ead.course.application.ports.service.CourseUserServicePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseUserUseCase implements CourseUserServicePort {

    private final AuthUserClient authUserClient;
    private final CourseUserRepositoryPort courseUserRepositoryPort;

    @Override
    public PageImpl<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        return authUserClient.buscaTodosUsuarioPorCurso(courseId, pageable);
    }

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return courseUserRepositoryPort.existeUsuarioNoCurso(courseModel, userId);
    }

    @Override
    @Transactional
    public void saveAndSendSubscriptionUserInCourse(CourseModel courseModel, UUID userId) {
        CourseUserModel courseUserModel = courseModel.converterParaCursoUsuarioModel(userId);
        courseUserRepositoryPort.salvar(courseUserModel);

        authUserClient.postSubscriptionUserInCourse(courseModel.getCourseId(), userId);

    }

    @Override
    public UserDTO getOneUserByUserId(UUID userId) {
        return authUserClient.buscarUmUsuarioPeloId(userId);
    }
}

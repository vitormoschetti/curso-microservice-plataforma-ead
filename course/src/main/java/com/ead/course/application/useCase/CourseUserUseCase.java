package com.ead.course.application.useCase;

import com.ead.course.adapter.inbound.controller.dto.ResponsePageDTO;
import com.ead.course.adapter.inbound.controller.dto.SubscriptionDTO;
import com.ead.course.adapter.inbound.controller.dto.UserDTO;
import com.ead.course.adapter.outbound.clients.AuthUserClientFeign;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.CourseUserModel;
import com.ead.course.application.ports.repository.CourseUserRepositoryPort;
import com.ead.course.application.ports.service.CourseUserServicePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseUserUseCase implements CourseUserServicePort {

    private final AuthUserClientFeign authUserClient;
    private final CourseUserRepositoryPort courseUserRepositoryPort;

    @Override
    public ResponsePageDTO<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        return (ResponsePageDTO<UserDTO>) authUserClient.getAllUsers(pageable, courseId).getBody();
    }

    @Override
    public boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId) {
        return courseUserRepositoryPort.existeUsuarioNoCurso(courseModel, userId);
    }

    @Override
    @Transactional
    public void saveAndSendSubscriptionUserInCourse(CourseModel courseModel, SubscriptionDTO subscriptionDTO) {
        CourseUserModel courseUserModel = courseModel.converterParaCursoUsuarioModel(subscriptionDTO.getUserId());
        courseUserRepositoryPort.salvar(courseUserModel);

        authUserClient.saveSubscriptionUserInCourse(subscriptionDTO.getUserId(), subscriptionDTO);

    }

    @Override
    public UserDTO getOneUserByUserId(UUID userId) {
        return authUserClient.getUser(userId).getBody();
    }
}

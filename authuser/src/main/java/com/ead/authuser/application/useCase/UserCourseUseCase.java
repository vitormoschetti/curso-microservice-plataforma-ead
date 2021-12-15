package com.ead.authuser.application.useCase;

import com.ead.authuser.adapter.outbound.client.CourseClient;
import com.ead.authuser.adapter.inbound.controller.dto.CourseDTO;
import com.ead.authuser.adapter.inbound.controller.dto.SubscriptionDTO;
import com.ead.authuser.application.model.UserCourseModel;
import com.ead.authuser.application.model.UserModel;
import com.ead.authuser.application.ports.repository.UserCourseRepositoryPort;
import com.ead.authuser.application.ports.service.UserCourseServicePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserCourseUseCase implements UserCourseServicePort {

    private final CourseClient courseClient;
    private final UserCourseRepositoryPort userCourseRepositoryPort;

    @Override
    public PageImpl<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {

        return courseClient.getAllCoursesByUser(userId, pageable);
    }

    @Override
    public boolean existsByUserAndCourseId(UserModel userModel, UUID courseId) {
        return userCourseRepositoryPort.existsByUserAndCourseId(userModel, courseId);
    }

    @Override
    public void save(UserModel userModel, SubscriptionDTO subscriptionDTO) {
        UserCourseModel userCourseModel = userModel.converterParaUserCourse(subscriptionDTO.getCourseId());
        userCourseRepositoryPort.save(userCourseModel);
    }
}
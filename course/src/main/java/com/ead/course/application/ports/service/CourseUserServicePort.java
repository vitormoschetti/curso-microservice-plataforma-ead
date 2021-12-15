package com.ead.course.application.ports.service;

import com.ead.course.adapter.inbound.controller.dto.UserDTO;
import com.ead.course.application.model.CourseModel;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserServicePort {

    PageImpl<UserDTO> getAllUsersByCourse(UUID userId, Pageable pageable);

    boolean existsByCourseAndUserId(CourseModel courseModel, UUID userId);

    void saveAndSendSubscriptionUserInCourse(CourseModel courseModel, UUID userId);

    UserDTO getOneUserByUserId(UUID userId);
}

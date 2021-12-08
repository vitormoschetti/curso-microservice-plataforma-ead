package com.ead.course.application.services;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.application.model.UserDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserService {
    PageImpl<UserDTO> getAllUsersByCourse(UUID userId, Pageable pageable);

    boolean existsByCourseAndUserId(CourseEntity courseEntity, UUID userId);

    void saveAndSendSubscriptionUserInCourse(CourseEntity courseEntity, UUID userId);

    UserDTO getOneUserByUserId(UUID userId);
}

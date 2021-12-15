package com.ead.authuser.application.ports.service;

import com.ead.authuser.adapter.inbound.controller.dto.CourseDTO;
import com.ead.authuser.adapter.inbound.controller.dto.SubscriptionDTO;
import com.ead.authuser.application.model.UserModel;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserCourseServicePort {
    PageImpl<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable);

    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

    void save(UserModel userModel, SubscriptionDTO subscriptionDTO);
}

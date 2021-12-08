package com.ead.authuser.application.services;

import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.application.model.CourseDTO;
import com.ead.authuser.application.model.SubscriptionDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserCourseService {
    PageImpl<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable);

    boolean existsByUserAndCourseId(UserEntity userEntity, UUID courseId);

    void save(UserEntity userEntity, SubscriptionDTO subscriptionDTO);
}

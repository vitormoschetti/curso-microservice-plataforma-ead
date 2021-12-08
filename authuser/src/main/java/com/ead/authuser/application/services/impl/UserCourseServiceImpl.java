package com.ead.authuser.application.services.impl;

import com.ead.authuser.adapter.clients.CourseClient;
import com.ead.authuser.adapter.repository.UserCourseRepository;
import com.ead.authuser.adapter.repository.entity.UserCourseEntity;
import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.application.model.CourseDTO;
import com.ead.authuser.application.model.SubscriptionDTO;
import com.ead.authuser.application.services.UserCourseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserCourseServiceImpl implements UserCourseService {

    private final CourseClient courseClient;
    private final UserCourseRepository userCourseRepository;

    @Override
    public PageImpl<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {

        return courseClient.getAllCoursesByUser(userId, pageable);
    }

    @Override
    public boolean existsByUserAndCourseId(UserEntity userEntity, UUID courseId) {
        return userCourseRepository.existsByUserAndCourseId(userEntity, courseId);
    }

    @Override
    public void save(UserEntity userEntity, SubscriptionDTO subscriptionDTO) {
        UserCourseEntity userCourseEntity = userEntity.converterParaUserCourse(subscriptionDTO.getCourseId());
        userCourseRepository.save(userCourseEntity);
    }
}

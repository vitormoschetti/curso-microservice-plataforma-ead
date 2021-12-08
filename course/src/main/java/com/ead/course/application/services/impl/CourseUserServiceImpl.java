package com.ead.course.application.services.impl;

import com.ead.course.adapter.clients.AuthUserClient;
import com.ead.course.adapter.repository.CourseUserRepository;
import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.repository.entity.CourseUserEntity;
import com.ead.course.application.model.UserDTO;
import com.ead.course.application.services.CourseUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final AuthUserClient authUserClient;
    private final CourseUserRepository courseUserRepository;

    @Override
    public PageImpl<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        return authUserClient.getAllUsersByCourse(courseId, pageable);
    }

    @Override
    public boolean existsByCourseAndUserId(CourseEntity courseEntity, UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(courseEntity, userId);
    }

    @Override
    @Transactional
    public void saveAndSendSubscriptionUserInCourse(CourseEntity courseEntity, UUID userId) {
        CourseUserEntity courseUserEntity = courseEntity.convertToCourseUserModel(userId);
        courseUserRepository.save(courseUserEntity);

        authUserClient.postSubscriptionUserInCourse(courseEntity.getCourseId(), userId);

    }

    @Override
    public UserDTO getOneUserByUserId(UUID userId) {
        return authUserClient.getOneUserById(userId);
    }
}

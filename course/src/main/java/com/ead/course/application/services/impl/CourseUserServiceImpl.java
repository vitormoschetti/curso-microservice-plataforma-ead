package com.ead.course.application.services.impl;

import com.ead.course.adapter.clients.UserClient;
import com.ead.course.application.model.UserDTO;
import com.ead.course.application.services.CourseUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final UserClient userClient;

    @Override
    public PageImpl<UserDTO> getAllUsersByCourse(UUID courseId, Pageable pageable) {
        return userClient.getAllUsersByCourse(courseId, pageable);
    }
}

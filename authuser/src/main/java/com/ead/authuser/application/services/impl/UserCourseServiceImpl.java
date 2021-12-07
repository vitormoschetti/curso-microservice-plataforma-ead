package com.ead.authuser.application.services.impl;

import com.ead.authuser.adapter.clients.CourseClient;
import com.ead.authuser.adapter.repository.UserCourseRepository;
import com.ead.authuser.application.model.CourseDTO;
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

    @Override
    public PageImpl<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable) {

        return courseClient.getAllCoursesByUser(userId, pageable);
    }
}

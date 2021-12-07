package com.ead.authuser.application.services;

import com.ead.authuser.application.model.CourseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserCourseService {
    PageImpl<CourseDTO> getAllCoursesByUser(UUID userId, Pageable pageable);
}

package com.ead.course.application.services;

import com.ead.course.application.model.UserDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CourseUserService {
    PageImpl<UserDTO> getAllUsersByCourse(UUID userId, Pageable pageable);
}

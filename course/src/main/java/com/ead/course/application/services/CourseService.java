package com.ead.course.application.services;

import com.ead.course.application.model.CourseDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    Optional<CourseDTO> delete(UUID courseEntity);

    void create(CourseDTO courseDTO);

    Optional<CourseDTO> update(UUID courseId, CourseDTO courseDTO);

    List<CourseDTO> getAll();

    Optional<CourseDTO> get(UUID courseId);
}

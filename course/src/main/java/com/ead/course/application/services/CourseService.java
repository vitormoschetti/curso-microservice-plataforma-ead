package com.ead.course.application.services;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.application.model.CourseDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    Optional<CourseDTO> delete(UUID courseEntity);

    void create(CourseDTO courseDTO);

    Optional<CourseDTO> update(UUID courseId, CourseDTO courseDTO);

    PageImpl<CourseDTO> getAll(SpecificationTemplate.CourseSpec spec, Pageable pageable);

    PageImpl<CourseDTO> getAll(Specification<CourseEntity> and, Pageable pageable);

    Optional<CourseDTO> get(UUID courseId);

    Optional<CourseEntity> getEntity(UUID courseId);

    Optional<CourseDTO> updateLastDate(UUID courseId);

}
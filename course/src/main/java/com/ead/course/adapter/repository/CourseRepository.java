package com.ead.course.adapter.repository;

import com.ead.course.adapter.repository.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID>, JpaSpecificationExecutor<CourseEntity> {
}

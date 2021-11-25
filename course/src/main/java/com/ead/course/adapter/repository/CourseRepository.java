package com.ead.course.adapter.repository;

import com.ead.course.adapter.repository.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseEntity, UUID> {
}

package com.ead.course.adapter.repository;

import com.ead.course.adapter.repository.entity.CourseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserEntity, UUID> {
}

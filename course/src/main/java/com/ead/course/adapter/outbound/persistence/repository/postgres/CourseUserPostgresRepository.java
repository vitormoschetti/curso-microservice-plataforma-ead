package com.ead.course.adapter.outbound.persistence.repository.postgres;

import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.CourseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CourseUserPostgresRepository extends JpaRepository<CourseUserEntity, UUID> {

    boolean existsByCourseAndUserId(CourseEntity courseEntity, UUID userId);

    List<CourseUserEntity> findAllByCourse_CourseId(UUID courseId);
}

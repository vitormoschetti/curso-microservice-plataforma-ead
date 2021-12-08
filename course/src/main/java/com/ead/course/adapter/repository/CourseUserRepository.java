package com.ead.course.adapter.repository;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.repository.entity.CourseUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserEntity, UUID> {

    boolean existsByCourseAndUserId(CourseEntity courseEntity, UUID userId);


//    @Query(value = "select * from tb_courses_users where course_course_id =: courseId", nativeQuery = true)
    List<CourseUserEntity> findAllByCourse_CourseId(UUID courseId);
}

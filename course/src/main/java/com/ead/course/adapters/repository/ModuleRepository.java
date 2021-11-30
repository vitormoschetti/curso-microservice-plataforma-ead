package com.ead.course.adapters.repository;

import com.ead.course.adapters.repository.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleEntity, UUID> {

    @Query(value = "SELECT * FROM TB_MODULES WHERE course_courseId = :courseId", nativeQuery = true)
    List<ModuleEntity> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    List<ModuleEntity> findAllByCourseIs(UUID courseId);
}

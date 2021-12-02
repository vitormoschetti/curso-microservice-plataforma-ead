package com.ead.course.adapter.repository;

import com.ead.course.adapter.repository.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleEntity, UUID>, JpaSpecificationExecutor<ModuleEntity> {

    @Query(value = "SELECT * FROM TB_MODULES WHERE course_courseId = :courseId", nativeQuery = true)
    List<ModuleEntity> findAllModulesIntoCourse(@Param("courseId") UUID courseId);

    Optional<ModuleEntity> findByCourse_CourseIdAndModuleId(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);

    List<ModuleEntity> findAllByCourse_CourseId(UUID courseId);
}

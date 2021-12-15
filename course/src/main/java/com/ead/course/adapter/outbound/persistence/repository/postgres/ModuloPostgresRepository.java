package com.ead.course.adapter.outbound.persistence.repository.postgres;

import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuloPostgresRepository extends JpaRepository<ModuleEntity, UUID>, JpaSpecificationExecutor<ModuleEntity> {

    Optional<ModuleEntity> findByCourse_CourseIdAndModuleId(@Param("courseId") UUID courseId, @Param("moduleId") UUID moduleId);

    List<ModuleEntity> findAllByCourse_CourseId(UUID courseId);
}

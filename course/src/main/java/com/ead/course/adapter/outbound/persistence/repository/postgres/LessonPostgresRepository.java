package com.ead.course.adapter.outbound.persistence.repository.postgres;

import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonPostgresRepository extends JpaRepository<LessonEntity, UUID>, JpaSpecificationExecutor<LessonEntity> {

    List<LessonEntity> findAllByModule_ModuleId(UUID moduleId);

    Optional<LessonEntity> findByModule_ModuleIdAndLessonId(@Param("moduleId") UUID moduleId, @Param("lessonId") UUID lessonId);

}

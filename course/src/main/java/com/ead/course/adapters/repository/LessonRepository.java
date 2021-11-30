package com.ead.course.adapters.repository;

import com.ead.course.adapters.repository.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonEntity, UUID> {

    @Query(value = "SELECT * FROM TB_LESSONS WHERE module_moduleId =: moduleId", nativeQuery = true)
    List<LessonEntity> findByAllLessonsIntoModule(UUID moduleId);
}

package com.ead.course.application.services;

import com.ead.course.adapter.repository.entity.LessonEntity;
import com.ead.course.adapter.repository.entity.ModuleEntity;
import com.ead.course.application.model.LessonDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    void create(LessonDTO lessonDTO, ModuleEntity moduleEntity);

    Optional<LessonDTO> delete(UUID moduleId, UUID lessonId);

    Optional<LessonDTO> update(UUID moduleId, UUID lessonId, LessonDTO lessonDTO);

    List<LessonDTO> getAll(Specification<LessonEntity> specification, Pageable pageable);

    Optional<LessonDTO> get(UUID moduleId, UUID lessonId);

}

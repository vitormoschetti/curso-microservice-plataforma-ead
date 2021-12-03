package com.ead.course.application.services;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.repository.entity.ModuleEntity;
import com.ead.course.application.model.ModuleDTO;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    Optional<ModuleDTO> delete(UUID moduleEntity, UUID moduleId);

    void create(ModuleDTO moduleDTO, CourseEntity courseDTO);

    PageImpl<ModuleDTO> getAll(Specification<ModuleEntity> specification, Pageable pageable);

    Optional<ModuleDTO> update(UUID courseId, UUID moduleId, ModuleDTO moduleDTO);

    Optional<ModuleDTO> get(UUID courseId, UUID moduleId);

    Optional<ModuleEntity> getEntity(UUID moduleId);

}

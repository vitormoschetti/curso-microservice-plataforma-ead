package com.ead.course.application.ports.service;

import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.adapter.inbound.controller.dto.ModuleDTO;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.ModuleModel;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ModuleServicePort {

    Optional<ModuleDTO> delete(UUID moduleEntity, UUID moduleId);

    void create(ModuleDTO moduleDTO, CourseModel courseModel);

    PageImpl<ModuleDTO> getAll(Specification<ModuleEntity> specification, Pageable pageable);

    Optional<ModuleDTO> update(UUID courseId, UUID moduleId, ModuleDTO moduleDTO);

    Optional<ModuleDTO> get(UUID courseId, UUID moduleId);

    Optional<ModuleModel> getEntity(UUID moduleId);

}

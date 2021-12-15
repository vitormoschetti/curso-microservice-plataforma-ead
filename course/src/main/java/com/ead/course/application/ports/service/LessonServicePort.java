package com.ead.course.application.ports.service;

import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.adapter.inbound.controller.dto.LessonDTO;
import com.ead.course.application.model.ModuleModel;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface LessonServicePort {

    void criar(LessonDTO lessonDTO, ModuleModel moduleModel);

    Optional<LessonDTO> deletar(UUID moduleId, UUID lessonId);

    Optional<LessonDTO> atualizar(UUID moduleId, UUID lessonId, LessonDTO lessonDTO);

    PageImpl<LessonDTO> buscarTodos(Specification<LessonEntity> specification, Pageable pageable);

    Optional<LessonDTO> buscar(UUID moduleId, UUID lessonId);

}

package com.ead.course.application.ports.service;

import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.adapter.inbound.controller.dto.CourseDTO;
import com.ead.course.application.model.CourseModel;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseServicePort {

    Optional<CourseDTO> deletar(UUID cursoId);

    void criar(CourseDTO courseDTO);

    Optional<CourseDTO> atualizar(UUID courseId, CourseDTO courseDTO);

    PageImpl<CourseDTO> buscarTodos(SpecificationTemplate.CursoSpec spec, Pageable pageable);

    PageImpl<CourseDTO> buscarTodos(Specification<CourseEntity> spec, Pageable pageable);

    Optional<CourseDTO> buscar(UUID courseId);

    Optional<CourseModel> buscarEntidade(UUID courseId);

    Optional<CourseDTO> atualizarDataUltimaAtualizacaoEntidade(UUID courseId);

}
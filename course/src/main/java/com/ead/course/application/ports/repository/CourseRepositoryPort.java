package com.ead.course.application.ports.repository;

import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.application.model.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseRepositoryPort {
    Optional<CourseModel> buscarPorId(UUID courseId);

    void deletar(CourseModel courseModel);

    void salvar(CourseModel courseModel);

    Page<CourseModel> buscarTodos(SpecificationTemplate.CursoSpec spec, Pageable pageable);

    Page<CourseModel> buscarTodos(Specification<CourseEntity> spec, Pageable pageable);
}

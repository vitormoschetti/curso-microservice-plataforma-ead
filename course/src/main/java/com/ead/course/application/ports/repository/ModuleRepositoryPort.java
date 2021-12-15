package com.ead.course.application.ports.repository;

import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.application.model.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepositoryPort {

    List<ModuleModel> buscarTodosPeloCurso(UUID courseId);

    void deletarTodos(List<ModuleModel> modules);

    Optional<ModuleModel> buscarModuloPeloCurso(UUID cursoId, UUID moduleId);

    void deletar(ModuleModel moduleModel);

    void salvar(ModuleModel moduleModel);

    Page<ModuleModel> buscarTodos(Specification<ModuleEntity> specification, Pageable pageable);

    Optional<ModuleModel> buscar(UUID moduleId);
}

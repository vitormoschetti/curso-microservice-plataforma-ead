package com.ead.course.application.ports.repository;

import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.application.model.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepositoryPort {

    List<LessonModel> buscarTodasAulasPelo(UUID moduleId);

    void deletarTodas(List<LessonModel> aulas);

    void salvar(LessonModel lessonModel);

    Page<LessonModel> buscarTodas(Specification<LessonEntity> specification, Pageable pageable);

    Optional<LessonModel> buscarAula(UUID moduleId, UUID lessonId);

    void deletar(LessonModel lessonModel);
}

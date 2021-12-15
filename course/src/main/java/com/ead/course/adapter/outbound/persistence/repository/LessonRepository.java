package com.ead.course.adapter.outbound.persistence.repository;

import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.adapter.outbound.persistence.repository.postgres.LessonPostgresRepository;
import com.ead.course.application.model.LessonModel;
import com.ead.course.application.ports.repository.LessonRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LessonRepository implements LessonRepositoryPort {

    private final LessonPostgresRepository repository;

    public LessonRepository(LessonPostgresRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<LessonModel> buscarTodasAulasPelo(UUID moduleId) {
        List<LessonEntity> aulaEntities = repository.findAllByModule_ModuleId(moduleId);
        return LessonModel.converter(aulaEntities);
    }

    @Override
    public void deletarTodas(List<LessonModel> lessons) {
        List<LessonEntity> aulasEntity = LessonEntity.converter(lessons);
        repository.deleteAll(aulasEntity);
    }

    @Override
    public void salvar(LessonModel lessonModel) {
        LessonEntity lessonEntity = LessonEntity.converter(lessonModel);
        repository.save(lessonEntity);
    }

    @Override
    public Page<LessonModel> buscarTodas(Specification<LessonEntity> specification, Pageable pageable) {
        Page<LessonEntity> aulaEntityPage = repository.findAll(specification, pageable);
        return LessonModel.converter(aulaEntityPage);
    }

    @Override
    public Optional<LessonModel> buscarAula(UUID moduleId, UUID lessonId) {

        Optional<LessonEntity> aulaEntityOptional = repository.findByModule_ModuleIdAndLessonId(moduleId, lessonId);

        if (aulaEntityOptional.isEmpty())
            return Optional.empty();

        LessonModel lessonModel = LessonModel.converter(aulaEntityOptional.get());

        return Optional.of(lessonModel);
    }

    @Override
    public void deletar(LessonModel lessonModel) {
        LessonEntity lessonEntity = LessonEntity.converter(lessonModel);
        repository.delete(lessonEntity);
    }
}

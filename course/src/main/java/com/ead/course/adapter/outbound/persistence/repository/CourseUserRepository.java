package com.ead.course.adapter.outbound.persistence.repository;

import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.CourseUserEntity;
import com.ead.course.adapter.outbound.persistence.repository.postgres.CourseUserPostgresRepository;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.CourseUserModel;
import com.ead.course.application.ports.repository.CourseUserRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CourseUserRepository implements CourseUserRepositoryPort {

    private final CourseUserPostgresRepository repository;

    public CourseUserRepository(CourseUserPostgresRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CourseUserModel> buscarTodosCursosPelo(UUID courseId) {
        List<CourseUserEntity> cursoUsuarioEntities = repository.findAllByCourse_CourseId(courseId);
        return CourseUserModel.converter(cursoUsuarioEntities);
    }

    @Override
    public void deletarTodos(List<CourseUserModel> courseUserModels) {
        List<CourseUserEntity> courseUserEntityList = CourseUserEntity.converter(courseUserModels);
        repository.deleteAll(courseUserEntityList);
    }

    @Override
    public boolean existeUsuarioNoCurso(CourseModel courseModel, UUID userId) {
        CourseEntity courseEntity = CourseEntity.converter(courseModel);
        return repository.existsByCourseAndUserId(courseEntity, userId);
    }

    @Override
    public void salvar(CourseUserModel courseUserModel) {
        CourseUserEntity courseUserEntity = CourseUserEntity.converter(courseUserModel);
        repository.save(courseUserEntity);
    }
}

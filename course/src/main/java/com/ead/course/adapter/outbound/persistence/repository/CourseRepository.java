package com.ead.course.adapter.outbound.persistence.repository;

import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.repository.postgres.CoursePostgresRepository;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.ports.repository.CourseRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CourseRepository implements CourseRepositoryPort {

    private final CoursePostgresRepository repository;

    public CourseRepository(CoursePostgresRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<CourseModel> buscarPorId(UUID courseId) {
        Optional<CourseEntity> cursoEntityOptional = repository.findById(courseId);

        if(cursoEntityOptional.isEmpty())
            return Optional.empty();

        return Optional.of(CourseModel.converter(cursoEntityOptional.get()));
    }

    @Override
    public void deletar(CourseModel courseModel) {
        CourseEntity courseEntity = CourseEntity.converter(courseModel);
        repository.delete(courseEntity);
    }

    @Override
    public void salvar(CourseModel courseModel) {
        CourseEntity courseEntity = CourseEntity.converter(courseModel);
        repository.save(courseEntity);
    }

    @Override
    public Page<CourseModel> buscarTodos(SpecificationTemplate.CursoSpec spec, Pageable pageable) {
        Page<CourseEntity> cursoEntityPage = repository.findAll(spec, pageable);
        return CourseModel.converter(cursoEntityPage);
    }

    @Override
    public Page<CourseModel> buscarTodos(Specification<CourseEntity> spec, Pageable pageable) {
        Page<CourseEntity> cursoEntityPage = repository.findAll(spec, pageable);
        return CourseModel.converter(cursoEntityPage);
    }
}

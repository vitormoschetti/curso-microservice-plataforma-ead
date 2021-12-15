package com.ead.course.adapter.outbound.persistence.repository;

import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.adapter.outbound.persistence.repository.postgres.ModuloPostgresRepository;
import com.ead.course.application.model.ModuleModel;
import com.ead.course.application.ports.repository.ModuleRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ModuleRepository implements ModuleRepositoryPort {

    private final ModuloPostgresRepository repository;

    public ModuleRepository(ModuloPostgresRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ModuleModel> buscarTodosPeloCurso(UUID courseId) {

        List<ModuleEntity> moduleEntityList = repository.findAllByCourse_CourseId(courseId);
        return ModuleModel.converter(moduleEntityList);
    }

    @Override
    public void deletarTodos(List<ModuleModel> modules) {
        List<ModuleEntity> moduleEntityList = ModuleEntity.converter(modules);
        repository.deleteAll(moduleEntityList);
    }

    @Override
    public Optional<ModuleModel> buscarModuloPeloCurso(UUID cursoId, UUID moduleId) {

        Optional<ModuleEntity> moduloEntityOptional = repository.findByCourse_CourseIdAndModuleId(cursoId, moduleId);

        if (moduloEntityOptional.isEmpty())
            return Optional.empty();

        ModuleModel moduleModel = ModuleModel.converter(moduloEntityOptional.get());
        return Optional.of(moduleModel);
    }

    @Override
    public void deletar(ModuleModel moduleModel) {
        ModuleEntity moduleEntity = ModuleEntity.converter(moduleModel);
        repository.delete(moduleEntity);
    }

    @Override
    public void salvar(ModuleModel moduleModel) {
        ModuleEntity moduleEntity = ModuleEntity.converter(moduleModel);
        repository.save(moduleEntity);
    }

    @Override
    public Page<ModuleModel> buscarTodos(Specification<ModuleEntity> specification, Pageable pageable) {

        Page<ModuleEntity> moduloEntityPage = repository.findAll(specification, pageable);

        return ModuleModel.converter(moduloEntityPage);
    }

    @Override
    public Optional<ModuleModel> buscar(UUID moduleId) {

        Optional<ModuleEntity> moduloEntityOptional = repository.findById(moduleId);

        if (moduloEntityOptional.isEmpty())
            return Optional.empty();

        ModuleModel moduleModel = ModuleModel.converter(moduloEntityOptional.get());
        return Optional.of(moduleModel);
    }
}

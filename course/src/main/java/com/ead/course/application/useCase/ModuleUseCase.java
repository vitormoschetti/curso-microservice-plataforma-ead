package com.ead.course.application.useCase;

import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.adapter.inbound.controller.dto.ModuleDTO;
import com.ead.course.application.model.LessonModel;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.ModuleModel;
import com.ead.course.application.ports.repository.LessonRepositoryPort;
import com.ead.course.application.ports.repository.ModuleRepositoryPort;
import com.ead.course.application.ports.service.CourseServicePort;
import com.ead.course.application.ports.service.ModuleServicePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModuleUseCase implements ModuleServicePort {

    private final ModuleRepositoryPort moduleRepositoryPort;
    private final LessonRepositoryPort lessonRepositoryPort;
    private final CourseServicePort courseServicePort;

    @Override
    public Optional<ModuleDTO> delete(UUID courseId, UUID moduleId) {

        Optional<ModuleModel> moduloModelOptional = moduleRepositoryPort.buscarModuloPeloCurso(courseId, moduleId);

        if (moduloModelOptional.isEmpty())
            return Optional.empty();

        List<LessonModel> lessonModels = lessonRepositoryPort.buscarTodasAulasPelo(moduleId);
        if (!lessonModels.isEmpty()) {
            lessonRepositoryPort.deletarTodas(lessonModels);
        }
        moduleRepositoryPort.deletar(moduloModelOptional.get());

        return Optional.of(new ModuleDTO());
    }

    @Override
    public void create(ModuleDTO moduleDTO, CourseModel courseModel) {
        final ModuleModel moduleModel = new ModuleModel(moduleDTO, courseModel);
        moduleRepositoryPort.salvar(moduleModel);
    }

    @Override
    public PageImpl<ModuleDTO> getAll(Specification<ModuleEntity> specification, Pageable pageable) {

        Page<ModuleModel> moduloModels = moduleRepositoryPort.buscarTodos(specification, pageable);

        if (moduloModels.isEmpty())
            return new PageImpl<>(null);

        return ModuleDTO.converter(moduloModels);
    }

    @Override
    @Transactional
    public Optional<ModuleDTO> update(UUID courseId, UUID moduleId, ModuleDTO moduleDTO) {

        Optional<ModuleModel> moduloModelOptional = moduleRepositoryPort.buscarModuloPeloCurso(courseId, moduleId);

        if (moduloModelOptional.isEmpty())
            return Optional.empty();

        ModuleModel moduleModel = moduloModelOptional.get();

        moduleModel.atualizar(moduleDTO);

        courseServicePort.atualizarDataUltimaAtualizacaoEntidade(courseId);
        moduleRepositoryPort.salvar(moduleModel);

        return Optional.of(ModuleDTO.converter(moduleModel));
    }

    @Override
    public Optional<ModuleDTO> get(UUID courseId, UUID moduleId) {

        Optional<ModuleModel> moduloModelOptional = moduleRepositoryPort.buscarModuloPeloCurso(courseId, moduleId);

        if (moduloModelOptional.isEmpty())
            return Optional.empty();

        ModuleModel moduleModel = moduloModelOptional.get();

        return Optional.of(ModuleDTO.converter(moduleModel));
    }

    @Override
    public Optional<ModuleModel> getEntity(UUID moduleId) {

        Optional<ModuleModel> moduloModelOptional = moduleRepositoryPort.buscar(moduleId);

        if (moduloModelOptional.isEmpty())
            return Optional.empty();

        return Optional.of(moduloModelOptional.get());
    }
}

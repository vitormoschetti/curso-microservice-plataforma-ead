package com.ead.course.application.useCase;

import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.adapter.inbound.controller.dto.LessonDTO;
import com.ead.course.application.model.LessonModel;
import com.ead.course.application.model.ModuleModel;
import com.ead.course.application.ports.repository.LessonRepositoryPort;
import com.ead.course.application.ports.service.CourseServicePort;
import com.ead.course.application.ports.service.LessonServicePort;
import com.ead.course.application.ports.service.ModuleServicePort;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LessonUseCase implements LessonServicePort {

    private final LessonRepositoryPort lessonRepositoryPort;
    private final ModuleServicePort moduleServicePort;
    private final CourseServicePort courseServicePort;

    @Override
    public void criar(LessonDTO lessonDTO, ModuleModel moduleModel) {
        LessonModel lessonModel = new LessonModel(moduleModel);
        BeanUtils.copyProperties(lessonDTO, lessonModel);
        lessonRepositoryPort.salvar(lessonModel);
    }

    @Override
    public Optional<LessonDTO> deletar(UUID moduleId, UUID lessonId) {

        Optional<LessonModel> aulaModelOptional = lessonRepositoryPort.buscarAula(moduleId, lessonId);

        if (aulaModelOptional.isEmpty())
            return Optional.empty();

        lessonRepositoryPort.deletar(aulaModelOptional.get());

        return Optional.of(new LessonDTO());
    }

    @Override
    @Transactional
    public Optional<LessonDTO> atualizar(UUID moduleId, UUID lessonId, LessonDTO lessonDTO) {

        Optional<LessonModel> aulaModelOptional = lessonRepositoryPort.buscarAula(moduleId, lessonId);

        if (aulaModelOptional.isEmpty())
            return Optional.empty();


        ModuleModel moduleModel = moduleServicePort.getEntity(moduleId).get();

        LessonModel lessonModel = aulaModelOptional.get();
        lessonModel.atualizar(lessonDTO);

        lessonRepositoryPort.salvar(lessonModel);
        courseServicePort.atualizarDataUltimaAtualizacaoEntidade(moduleModel.getCourseModel().getCourseId());

        return Optional.of(LessonDTO.converter(lessonModel));
    }

    @Override
    public PageImpl<LessonDTO> buscarTodos(Specification<LessonEntity> specification, Pageable pageable) {

        Page<LessonModel> aulaModelPage = lessonRepositoryPort.buscarTodas(specification, pageable);

        if(aulaModelPage.isEmpty())
            return new PageImpl<>(null);

        return LessonDTO.converter(aulaModelPage);
    }

    @Override
    public Optional<LessonDTO> buscar(UUID moduleId, UUID lessonId) {

        Optional<LessonModel> aulaModelOptional = lessonRepositoryPort.buscarAula(moduleId, lessonId);

        if(aulaModelOptional.isEmpty())
            return Optional.empty();

        return Optional.of(LessonDTO.converter(aulaModelOptional.get()));
    }
}

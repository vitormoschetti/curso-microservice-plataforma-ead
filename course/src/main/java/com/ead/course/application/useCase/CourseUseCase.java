package com.ead.course.application.useCase;

import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.adapter.inbound.controller.dto.CourseDTO;
import com.ead.course.application.model.LessonModel;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.CourseUserModel;
import com.ead.course.application.model.ModuleModel;
import com.ead.course.application.ports.repository.CourseRepositoryPort;
import com.ead.course.application.ports.repository.CourseUserRepositoryPort;
import com.ead.course.application.ports.repository.LessonRepositoryPort;
import com.ead.course.application.ports.repository.ModuleRepositoryPort;
import com.ead.course.application.ports.service.CourseServicePort;
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
public class CourseUseCase implements CourseServicePort {

    private final CourseRepositoryPort courseRepositoryPort;
    private final ModuleRepositoryPort moduleRepositoryPort;
    private final LessonRepositoryPort lessonRepositoryPort;
    private final CourseUserRepositoryPort courseUserRepositoryPort;


    @Override
    @Transactional
    public Optional<CourseDTO> deletar(UUID courseId) {


        Optional<CourseModel> courseEntityOptional = courseRepositoryPort.buscarPorId(courseId);

        if (courseEntityOptional.isEmpty())
            return Optional.empty();

        final CourseModel courseModel = courseEntityOptional.get();

        List<ModuleModel> modules = moduleRepositoryPort.buscarTodosPeloCurso(courseModel.getCourseId());
        if (!modules.isEmpty()) {
            for (ModuleModel module : modules) {
                List<LessonModel> lessonModels = lessonRepositoryPort.buscarTodasAulasPelo(module.getModuleId());
                if (!lessonModels.isEmpty()) {
                    lessonRepositoryPort.deletarTodas(lessonModels);
                }
            }
            moduleRepositoryPort.deletarTodos(modules);
        }

        List<CourseUserModel> courseUserEntities = courseUserRepositoryPort.buscarTodosCursosPelo(courseModel.getCourseId());
        if(!courseUserEntities.isEmpty())
            courseUserRepositoryPort.deletarTodos(courseUserEntities);

        courseRepositoryPort.deletar(courseModel);

        return Optional.of(new CourseDTO());
    }

    @Override
    public void criar(CourseDTO courseDTO) {
        final CourseModel courseModel = new CourseModel(courseDTO);
        courseRepositoryPort.salvar(courseModel);
    }

    @Override
    public Optional<CourseDTO> atualizar(UUID courseId, CourseDTO courseDTO) {

        Optional<CourseModel> cursoModelOptional = courseRepositoryPort.buscarPorId(courseId);

        if (cursoModelOptional.isEmpty())
            return Optional.empty();

        final CourseModel courseModel = cursoModelOptional.get();

        courseModel.atualizar(courseDTO);

        courseRepositoryPort.salvar(courseModel);

        return Optional.of(CourseDTO.converter(courseModel));
    }

    @Override
    public Optional<CourseDTO> atualizarDataUltimaAtualizacaoEntidade(UUID courseId) {

        Optional<CourseModel> cursoModelOptional = courseRepositoryPort.buscarPorId(courseId);

        if (cursoModelOptional.isEmpty())
            return Optional.empty();

        final CourseModel courseModel = cursoModelOptional.get();

        courseModel.atualizar();

        courseRepositoryPort.salvar(courseModel);

        return Optional.of(CourseDTO.converter(courseModel));
    }

    @Override
    public PageImpl<CourseDTO> buscarTodos(SpecificationTemplate.CursoSpec spec, Pageable pageable) {
        Page<CourseModel> cursoModelsPage = courseRepositoryPort.buscarTodos(spec, pageable);
        return CourseDTO.converter(cursoModelsPage);
    }

    @Override
    public PageImpl<CourseDTO> buscarTodos(Specification<CourseEntity> spec, Pageable pageable) {
        Page<CourseModel> cursoModelsPage = courseRepositoryPort.buscarTodos(spec, pageable);
        return CourseDTO.converter(cursoModelsPage);
    }

    @Override
    public Optional<CourseDTO> buscar(UUID courseId) {

        Optional<CourseModel> cursoModelOptional = courseRepositoryPort.buscarPorId(courseId);

        if (cursoModelOptional.isEmpty())
            return Optional.empty();

        CourseModel courseModel = cursoModelOptional.get();

        return Optional.of(CourseDTO.converter(courseModel));

    }

    @Override
    public Optional<CourseModel> buscarEntidade(UUID courseId) {

        Optional<CourseModel> cursoModelOptional = courseRepositoryPort.buscarPorId(courseId);

        if (cursoModelOptional.isEmpty())
            return Optional.empty();

        CourseModel courseModel = cursoModelOptional.get();

        return Optional.of(courseModel);

    }
}

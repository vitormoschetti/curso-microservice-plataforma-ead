package com.ead.course.application.services.impl;

import com.ead.course.adapter.repository.LessonRepository;
import com.ead.course.adapter.repository.ModuleRepository;
import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.repository.entity.LessonEntity;
import com.ead.course.adapter.repository.entity.ModuleEntity;
import com.ead.course.application.model.ModuleDTO;
import com.ead.course.application.services.CourseService;
import com.ead.course.application.services.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final CourseService courseService;

    @Override
    public Optional<ModuleDTO> delete(UUID courseId, UUID moduleId) {

        Optional<ModuleEntity> moduleEntityOptional = moduleRepository.findByCourse_CourseIdAndModuleId(courseId, moduleId);

        if (moduleEntityOptional.isEmpty())
            return Optional.empty();

        List<LessonEntity> lessons = lessonRepository.findAllByModule_ModuleId(moduleId);
        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.delete(moduleEntityOptional.get());

        return Optional.of(new ModuleDTO());
    }

    @Override
    public void create(ModuleDTO moduleDTO, CourseEntity courseEntity) {
        final ModuleEntity moduleEntity = new ModuleEntity(courseEntity);
        BeanUtils.copyProperties(moduleDTO, moduleEntity);
        moduleRepository.save(moduleEntity);
    }

    @Override
    public List<ModuleDTO> getAll(Specification<ModuleEntity> specification, Pageable pageable) {

        Page<ModuleEntity> moduleEntities = moduleRepository.findAll(specification, pageable);

        if (moduleEntities.isEmpty())
            return List.of();

        return ModuleDTO.convert(moduleEntities);
    }

    @Override
    @Transactional
    public Optional<ModuleDTO> update(UUID courseId, UUID moduleId, ModuleDTO moduleDTO) {

        Optional<ModuleEntity> moduleEntityOptional = moduleRepository.findByCourse_CourseIdAndModuleId(courseId, moduleId);

        if (moduleEntityOptional.isEmpty())
            return Optional.empty();

        ModuleEntity moduleEntity = moduleEntityOptional.get();

        moduleEntity.update(moduleDTO);

        courseService.updateLastDate(courseId);
        moduleRepository.save(moduleEntity);

        return Optional.of(ModuleDTO.convert(moduleEntity));
    }

    @Override
    public Optional<ModuleDTO> get(UUID courseId, UUID moduleId) {

        Optional<ModuleEntity> moduleEntityOptional = moduleRepository.findByCourse_CourseIdAndModuleId(courseId, moduleId);

        if (moduleEntityOptional.isEmpty())
            return Optional.empty();

        ModuleEntity moduleEntity = moduleEntityOptional.get();

        return Optional.of(ModuleDTO.convert(moduleEntity));
    }

    @Override
    public Optional<ModuleEntity> getEntity(UUID moduleId) {

        Optional<ModuleEntity> moduleEntityOptional = moduleRepository.findById(moduleId);

        if (moduleEntityOptional.isEmpty())
            return Optional.empty();

        return Optional.of(moduleEntityOptional.get());
    }
}

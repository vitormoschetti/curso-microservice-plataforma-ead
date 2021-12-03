package com.ead.course.application.services.impl;

import com.ead.course.adapter.repository.LessonRepository;
import com.ead.course.adapter.repository.entity.LessonEntity;
import com.ead.course.adapter.repository.entity.ModuleEntity;
import com.ead.course.application.model.LessonDTO;
import com.ead.course.application.services.CourseService;
import com.ead.course.application.services.LessonService;
import com.ead.course.application.services.ModuleService;
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
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;
    private final CourseService courseService;

    @Override
    public void create(LessonDTO lessonDTO, ModuleEntity moduleEntity) {
        LessonEntity lessonEntity = new LessonEntity(moduleEntity);
        BeanUtils.copyProperties(lessonDTO, lessonEntity);
        lessonRepository.save(lessonEntity);
    }

    @Override
    public Optional<LessonDTO> delete(UUID moduleId, UUID lessonId) {

        Optional<LessonEntity> lessonEntityOptional = lessonRepository.findByModule_ModuleIdAndLessonId(moduleId, lessonId);

        if (lessonEntityOptional.isEmpty())
            return Optional.empty();

        lessonRepository.delete(lessonEntityOptional.get());

        return Optional.of(new LessonDTO());
    }

    @Override
    @Transactional
    public Optional<LessonDTO> update(UUID moduleId, UUID lessonId, LessonDTO lessonDTO) {

        Optional<LessonEntity> lessonEntityOptional = lessonRepository.findByModule_ModuleIdAndLessonId(moduleId, lessonId);

        if (lessonEntityOptional.isEmpty())
            return Optional.empty();


        ModuleEntity moduleEntity = moduleService.getEntity(moduleId).get();

        LessonEntity lessonEntity = lessonEntityOptional.get();
        lessonEntity.update(lessonDTO);

        lessonRepository.save(lessonEntity);
        courseService.updateLastDate(moduleEntity.getCourse().getCourseId());

        return Optional.of(LessonDTO.convert(lessonEntity));
    }

    @Override
    public PageImpl<LessonDTO> getAll(Specification<LessonEntity> specification, Pageable pageable) {

        Page<LessonEntity> lessonEntities = lessonRepository.findAll(specification, pageable);

        if(lessonEntities.isEmpty())
            return new PageImpl<>(null);

        return LessonDTO.convert(lessonEntities);
    }

    @Override
    public Optional<LessonDTO> get(UUID moduleId, UUID lessonId) {

        Optional<LessonEntity> lessonEntityOptional = lessonRepository.findByModule_ModuleIdAndLessonId(moduleId, lessonId);

        if(lessonEntityOptional.isEmpty())
            return Optional.empty();

        return Optional.of(LessonDTO.convert(lessonEntityOptional.get()));
    }
}

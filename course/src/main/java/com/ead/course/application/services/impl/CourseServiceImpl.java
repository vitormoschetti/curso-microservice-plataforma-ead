package com.ead.course.application.services.impl;

import com.ead.course.adapter.repository.CourseRepository;
import com.ead.course.adapter.repository.LessonRepository;
import com.ead.course.adapter.repository.ModuleRepository;
import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.adapter.repository.entity.LessonEntity;
import com.ead.course.adapter.repository.entity.ModuleEntity;
import com.ead.course.adapter.specification.SpecificationTemplate;
import com.ead.course.application.model.CourseDTO;
import com.ead.course.application.services.CourseService;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;


    @Override
    @Transactional
    public Optional<CourseDTO> delete(UUID courseId) {


        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if (courseEntityOptional.isEmpty())
            return Optional.empty();

        final CourseEntity courseEntity = courseEntityOptional.get();

        List<ModuleEntity> modules = moduleRepository.findAllByCourse_CourseId(courseEntity.getCourseId());
        if (!modules.isEmpty()) {
            for (ModuleEntity module : modules) {
                List<LessonEntity> lessons = lessonRepository.findAllByModule_ModuleId(module.getModuleId());
                if (!lessons.isEmpty()) {
                    lessonRepository.deleteAll(lessons);
                }
            }
            moduleRepository.deleteAll(modules);
        }
        courseRepository.delete(courseEntity);

        return Optional.of(new CourseDTO());
    }

    @Override
    public void create(CourseDTO courseDTO) {
        final CourseEntity courseEntity = new CourseEntity();
        BeanUtils.copyProperties(courseDTO, courseEntity);
        courseRepository.save(courseEntity);
    }

    @Override
    public Optional<CourseDTO> update(UUID courseId, CourseDTO courseDTO) {

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if (courseEntityOptional.isEmpty())
            return Optional.empty();

        final CourseEntity courseEntity = courseEntityOptional.get();

        courseEntity.update(courseDTO);

        courseRepository.save(courseEntity);

        return Optional.of(CourseDTO.convert(courseEntity));
    }

    @Override
    public Optional<CourseDTO> updateLastDate(UUID courseId) {

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if (courseEntityOptional.isEmpty())
            return Optional.empty();

        final CourseEntity courseEntity = courseEntityOptional.get();

        courseEntity.update();

        courseRepository.save(courseEntity);

        return Optional.of(CourseDTO.convert(courseEntity));
    }

    @Override
    public PageImpl<CourseDTO> getAll(SpecificationTemplate.CourseSpec spec, Pageable pageable) {
        Page<CourseEntity> courseRepositoryAll = courseRepository.findAll(spec, pageable);
        return CourseDTO.convert(courseRepositoryAll);
    }

    @Override
    public PageImpl<CourseDTO> getAll(Specification<CourseEntity> spec, Pageable pageable) {
        Page<CourseEntity> courseRepositoryAll = courseRepository.findAll(spec, pageable);
        return CourseDTO.convert(courseRepositoryAll);
    }

    @Override
    public Optional<CourseDTO> get(UUID courseId) {

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if (courseEntityOptional.isEmpty())
            return Optional.empty();

        CourseEntity courseEntity = courseEntityOptional.get();

        return Optional.of(CourseDTO.convert(courseEntity));

    }

    @Override
    public Optional<CourseEntity> getEntity(UUID courseId) {

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if (courseEntityOptional.isEmpty())
            return Optional.empty();

        CourseEntity courseEntity = courseEntityOptional.get();

        return Optional.of(courseEntity);

    }
}

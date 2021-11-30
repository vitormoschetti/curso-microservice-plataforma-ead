package com.ead.course.application.services.impl;

import com.ead.course.adapters.repository.CourseRepository;
import com.ead.course.adapters.repository.LessonRepository;
import com.ead.course.adapters.repository.ModuleRepository;
import com.ead.course.adapters.repository.entity.CourseEntity;
import com.ead.course.adapters.repository.entity.LessonEntity;
import com.ead.course.adapters.repository.entity.ModuleEntity;
import com.ead.course.application.model.CourseDTO;
import com.ead.course.application.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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

        List<ModuleEntity> modules = moduleRepository.findAllModulesIntoCourse(courseEntity.getCourseId());
        if (!modules.isEmpty()) {
            for (ModuleEntity module : modules) {
                List<LessonEntity> lessons = lessonRepository.findByAllLessonsIntoModule(module.getModuleId());
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
    public List<CourseDTO> getAll() {
        return CourseDTO.convert(courseRepository.findAll());
    }

    @Override
    public Optional<CourseDTO> get(UUID courseId) {

        Optional<CourseEntity> courseEntityOptional = courseRepository.findById(courseId);

        if (courseEntityOptional.isEmpty())
            return Optional.empty();

        CourseEntity courseEntity = courseEntityOptional.get();

        return Optional.of(CourseDTO.convert(courseEntity));

    }
}

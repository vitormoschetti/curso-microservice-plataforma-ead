package com.ead.course.application.services.impl;

import com.ead.course.adapters.repository.LessonRepository;
import com.ead.course.adapters.repository.ModuleRepository;
import com.ead.course.adapters.repository.entity.LessonEntity;
import com.ead.course.adapters.repository.entity.ModuleEntity;
import com.ead.course.application.services.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Override
    public void delete(ModuleEntity moduleEntity) {

        List<LessonEntity> lessons = lessonRepository.findByAllLessonsIntoModule(moduleEntity.getModuleId());
        if (!lessons.isEmpty()) {
            lessonRepository.deleteAll(lessons);
        }
        moduleRepository.delete(moduleEntity);

    }
}

package com.ead.course.application.services.impl;

import com.ead.course.adapter.repository.LessonRepository;
import com.ead.course.application.services.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

}

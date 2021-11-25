package com.ead.course.application.services.impl;

import com.ead.course.adapter.repository.CourseRepository;
import com.ead.course.application.services.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

}

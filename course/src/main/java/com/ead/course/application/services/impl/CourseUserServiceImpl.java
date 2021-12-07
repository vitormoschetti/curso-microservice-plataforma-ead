package com.ead.course.application.services.impl;

import com.ead.course.adapter.repository.CourseUserRepository;
import com.ead.course.application.services.CourseUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;


}

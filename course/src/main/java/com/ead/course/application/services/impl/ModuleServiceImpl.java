package com.ead.course.application.services.impl;

import com.ead.course.adapter.repository.ModuleRepository;
import com.ead.course.application.services.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

}

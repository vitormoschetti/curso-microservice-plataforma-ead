package com.ead.course.application.model;

import com.ead.course.adapter.inbound.controller.dto.ModuleDTO;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class ModuleModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID moduleId;
    private String title;
    private String description;
    private LocalDateTime creationDate;
    private CourseModel courseModel;
    private Set<LessonModel> lessons;

    public ModuleModel(ModuleDTO moduleDTO, CourseModel courseModel) {
        this.moduleId = moduleDTO.getModuleId();
        this.title = moduleDTO.getTitle();
        this.description = moduleDTO.getDescription();
        this.creationDate = LocalDateTime.now(ZoneId.of("UTC"));
        this.courseModel = courseModel;
    }

    private ModuleModel(ModuleEntity moduleEntity) {
        this.moduleId = moduleEntity.getModuleId();
        this.title = moduleEntity.getTitle();
        this.description = moduleEntity.getDescription();
        this.creationDate = moduleEntity.getCreationDate();
    }

    public static ModuleModel converter(ModuleEntity moduleEntity) {
        ModuleModel moduleModel = new ModuleModel(moduleEntity);
        return moduleModel;
    }

    public static List<ModuleModel> converter(List<ModuleEntity> moduleEntityList) {
        return moduleEntityList.stream().map(ModuleModel::new).collect(Collectors.toList());
    }

    public static Page<ModuleModel> converter(Page<ModuleEntity> moduloEntityPage) {
        List<ModuleEntity> moduleEntityList = moduloEntityPage.getContent();
        List<ModuleModel> moduleModels = moduleEntityList.stream().map(ModuleModel::new).collect(Collectors.toList());
        return new PageImpl<>(moduleModels);
    }

    public void atualizar(ModuleDTO moduleDTO) {
        this.title = moduleDTO.getTitle();
        this.description = moduleDTO.getDescription();
    }
}

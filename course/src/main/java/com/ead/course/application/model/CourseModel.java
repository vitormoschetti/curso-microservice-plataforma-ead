package com.ead.course.application.model;

import com.ead.course.adapter.inbound.controller.dto.CourseDTO;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.application.model.enums.CourseLevel;
import com.ead.course.application.model.enums.CourseStatus;
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
public class CourseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID courseId;
    private String name;
    private String description;
    private String imageUrl;
    private LocalDateTime creationDate;
    private LocalDateTime lastUpdateDate;
    private CourseStatus courseStatus;
    private CourseLevel courseLevel;
    private UUID userInstructor;
    private Set<ModuleModel> modules;
    private Set<CourseUserModel> courseUser;

    private CourseModel(CourseEntity courseEntity) {
        this.courseId = courseEntity.getCourseId();
        this.name = courseEntity.getName();
        this.description = courseEntity.getDescription();
        this.imageUrl = courseEntity.getImageUrl();
        this.creationDate = courseEntity.getCreationDate();
        this.lastUpdateDate = courseEntity.getLastUpdateDate();
        this.courseStatus = courseEntity.getCourseStatus();
        this.courseLevel = courseEntity.getCourseLevel();
        this.userInstructor = courseEntity.getUserInstructor();
    }

    public CourseModel(CourseDTO courseDTO) {
        this.courseId = courseDTO.getCourseId();
        this.name = courseDTO.getName();
        this.description = courseDTO.getDescription();
        this.imageUrl = courseDTO.getImageUrl();
        this.courseStatus = courseDTO.getCourseStatus();
        this.courseLevel = courseDTO.getCourseLevel();
        this.userInstructor = courseDTO.getUserInstructor();
        this.creationDate = LocalDateTime.now(ZoneId.of("UTC"));
        this.lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public static CourseModel converter(CourseEntity courseEntity) {
        return new CourseModel(courseEntity);
    }

    public static Page<CourseModel> converter(Page<CourseEntity> cursoEntitiesPage) {
        List<CourseEntity> cursoEntities = cursoEntitiesPage.getContent();
        List<CourseModel> courseModels = converter(cursoEntities);
        return new PageImpl<>(courseModels);
    }

    private static List<CourseModel> converter(List<CourseEntity> cursoEntities) {
        return cursoEntities.stream().map(CourseModel::new).collect(Collectors.toList());
    }

    public void atualizar(CourseDTO dto) {
        name = dto.getName();
        description = dto.getDescription();
        imageUrl = dto.getImageUrl();
        courseStatus = dto.getCourseStatus();
        courseLevel = dto.getCourseLevel();
        userInstructor = dto.getUserInstructor();
        lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public void atualizar() {
        lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

    public CourseUserModel converterParaCursoUsuarioModel(UUID userId) {
        return new CourseUserModel(this, userId);
    }
}

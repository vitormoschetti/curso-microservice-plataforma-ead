package com.ead.course.application.model;

import com.ead.course.adapter.inbound.controller.dto.LessonDTO;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class LessonModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID lessonId;
    private String title;
    private String description;
    private String videoUrl;
    private LocalDateTime creationDate;
    private ModuleModel moduleModel;


    public LessonModel(ModuleModel moduleModel) {
        this.creationDate = LocalDateTime.now(ZoneId.of("UTC"));
        this.moduleModel = moduleModel;
    }

    private LessonModel(LessonEntity lessonEntity) {
        this.lessonId = lessonEntity.getLessonId();
        this.title = lessonEntity.getTitle();
        this.description = lessonEntity.getDescription();
        this.videoUrl = lessonEntity.getVideoUrl();
        this.creationDate = lessonEntity.getCreationDate();
    }


    public static List<LessonModel> converter(List<LessonEntity> aulaEntities) {
        return aulaEntities.stream().map(LessonModel::new).collect(Collectors.toList());
    }

    public static Page<LessonModel> converter(Page<LessonEntity> aulaEntityPage) {
        List<LessonEntity> aulaEntities = aulaEntityPage.getContent();
        List<LessonModel> lessonModels = converter(aulaEntities);
        return new PageImpl<>(lessonModels);
    }

    public static LessonModel converter(LessonEntity lessonEntity) {
        return new LessonModel(lessonEntity);
    }

    public void atualizar(LessonDTO lessonDTO) {
        title = lessonDTO.getTitle();
        description = lessonDTO.getDescription();
        videoUrl = lessonDTO.getVideoUrl();
    }


}

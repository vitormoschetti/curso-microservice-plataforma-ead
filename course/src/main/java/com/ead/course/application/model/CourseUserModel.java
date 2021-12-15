package com.ead.course.application.model;

import com.ead.course.adapter.outbound.persistence.entity.CourseUserEntity;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class CourseUserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private CourseModel course;
    private UUID userId;

    public CourseUserModel(CourseModel courseModel, UUID userId){
        this.course = courseModel;
        this.userId = userId;
    }

    private CourseUserModel(CourseUserEntity courseUserEntity) {
        this.id = courseUserEntity.getId();
        this.course = CourseModel.converter(courseUserEntity.getCourse());
        this.userId = courseUserEntity.getUserId();
    }

    public static List<CourseUserModel> converter(List<CourseUserEntity> cursoUsuarioEntities) {
        return cursoUsuarioEntities.stream().map(CourseUserModel::new).collect(Collectors.toList());
    }
}

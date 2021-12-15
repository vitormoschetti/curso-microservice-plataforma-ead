package com.ead.course.adapter.outbound.persistence.entity;

import com.ead.course.application.model.CourseUserModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_COURSES_USERS")
public class CourseUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private CourseEntity course;

    @Column(nullable = false, name = "userId")
    private UUID userId;


    private CourseUserEntity(CourseUserModel courseUserModel){
        this.id = courseUserModel.getId();
        this.course = CourseEntity.converter(courseUserModel.getCourse());
        this.userId = courseUserModel.getUserId();
    }

    public static List<CourseUserEntity> converter(List<CourseUserModel> courseUserModels) {
        return courseUserModels.stream().map(CourseUserEntity::new).collect(Collectors.toList());
    }

    public static CourseUserEntity converter(CourseUserModel courseUserModel) {
        return new CourseUserEntity(courseUserModel);
    }
}

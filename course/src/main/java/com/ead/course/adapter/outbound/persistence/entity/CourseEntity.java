package com.ead.course.adapter.outbound.persistence.entity;

import com.ead.course.adapter.inbound.controller.dto.CourseDTO;
import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.enums.CourseLevel;
import com.ead.course.application.model.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_COURSES")
public class CourseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "courseId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID courseId;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(nullable = false, name = "creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;

    @Column(nullable = false, name = "lastUpdateDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastUpdateDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "courseStatus")
    private CourseStatus courseStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "courseLevel")
    private CourseLevel courseLevel;

    @Column(nullable = false, name = "userInstructor")
    private UUID userInstructor;

    @Fetch(FetchMode.SUBSELECT)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<ModuleEntity> modules;

    @Column(name = "coursesUsers")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private Set<CourseUserEntity> coursesUsers;


    public CourseEntity() {
        creationDate = LocalDateTime.now(ZoneId.of("UTC"));
        lastUpdateDate = LocalDateTime.now(ZoneId.of("UTC"));
    }

    private CourseEntity(CourseModel courseModel) {
        this.courseId = courseModel.getCourseId();
        this.name = courseModel.getName();
        this.description = courseModel.getDescription();
        this.imageUrl = courseModel.getImageUrl();
        this.creationDate = courseModel.getCreationDate();
        this.lastUpdateDate = courseModel.getLastUpdateDate();
        this.courseStatus = courseModel.getCourseStatus();
        this.courseLevel = courseModel.getCourseLevel();
        this.userInstructor = courseModel.getUserInstructor();
    }

    public static CourseEntity converter(CourseModel courseModel) {
        return new CourseEntity(courseModel);
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

    public CourseUserEntity converterParaCursoUsuarioModel(UUID userId) {
        return new CourseUserEntity(null, this, userId);
    }
}
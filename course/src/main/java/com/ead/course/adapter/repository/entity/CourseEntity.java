package com.ead.course.adapter.repository.entity;

import com.ead.course.application.model.enums.CourseLevel;
import com.ead.course.application.model.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
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


    private UUID userInstructor;


}

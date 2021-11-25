package com.ead.course.adapter.repository.entity;


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
@Table(name = "TB_LESSONS")
public class LessonEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Setter(AccessLevel.NONE)
    @Column(name = "lessonId")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID lessonId;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false, name = "videoUrl")
    private String videoUrl;

    @Column(nullable = false, name = "creationDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;



}

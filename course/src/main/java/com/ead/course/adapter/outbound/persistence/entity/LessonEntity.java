package com.ead.course.adapter.outbound.persistence.entity;


import com.ead.course.application.model.LessonModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Entity
@NoArgsConstructor
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ModuleEntity module;

    private LessonEntity(LessonModel aula) {
        this.lessonId = aula.getLessonId();
        this.title = aula.getTitle();
        this.description = aula.getDescription();
        this.videoUrl = aula.getVideoUrl();
        this.creationDate = aula.getCreationDate();
    }


    public static List<LessonEntity> converter(List<LessonModel> aulas) {
        return aulas.stream().map(LessonEntity::new).collect(Collectors.toList());
    }

    public static LessonEntity converter(LessonModel lessonModel) {
        return new LessonEntity(lessonModel);
    }
}

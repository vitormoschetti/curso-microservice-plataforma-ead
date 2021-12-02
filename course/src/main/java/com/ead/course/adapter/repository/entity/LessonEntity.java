package com.ead.course.adapter.repository.entity;


import com.ead.course.application.model.LessonDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

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

    public LessonEntity(ModuleEntity moduleEntity) {
        creationDate = LocalDateTime.now(ZoneId.of("UTC"));
        module = moduleEntity;
    }

    public void update(LessonDTO lessonDTO) {
        title = lessonDTO.getTitle();
        description = lessonDTO.getDescription();
        videoUrl = lessonDTO.getVideoUrl();
    }
}

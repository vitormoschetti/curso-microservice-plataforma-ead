package com.ead.course.application.model;

import com.ead.course.adapter.repository.entity.LessonEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID lessonId;

    @NotBlank
    private String title;

    private String description;
    
    @NotBlank
    private String videoUrl;

    private LessonDTO(LessonEntity l) {
        lessonId = l.getLessonId();
        title = l.getTitle();
        description = l.getDescription();
        videoUrl = l.getVideoUrl();
    }

    public static LessonDTO convert(LessonEntity lessonEntity) {
        return new LessonDTO(lessonEntity);
    }

    public static List<LessonDTO> convert(Page<LessonEntity> lessonEntities) {
        return lessonEntities.stream().map(LessonDTO::new).collect(Collectors.toList());
    }
}

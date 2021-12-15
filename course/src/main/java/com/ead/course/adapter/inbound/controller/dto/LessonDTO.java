package com.ead.course.adapter.inbound.controller.dto;

import com.ead.course.application.model.LessonModel;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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

    private LessonDTO(LessonModel lessonModel) {
        lessonId = lessonModel.getLessonId();
        title = lessonModel.getTitle();
        description = lessonModel.getDescription();
        videoUrl = lessonModel.getVideoUrl();
    }

    public static LessonDTO converter(LessonModel lessonModel) {
        return new LessonDTO(lessonModel);
    }

    public static PageImpl<LessonDTO> converter(Page<LessonModel> aulaModels) {

        List<LessonDTO> lessonDTOS = aulaModels.stream().map(LessonDTO::new).collect(Collectors.toList());

        return new PageImpl<LessonDTO>(lessonDTOS);
    }
}

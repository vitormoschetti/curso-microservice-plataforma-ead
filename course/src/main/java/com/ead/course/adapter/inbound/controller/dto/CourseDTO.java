package com.ead.course.adapter.inbound.controller.dto;

import com.ead.course.application.model.CourseModel;
import com.ead.course.application.model.enums.CourseLevel;
import com.ead.course.application.model.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID courseId;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String imageUrl;

    @NotNull
    private CourseStatus courseStatus;

    @NotNull
    private UUID userInstructor;

    @NotNull
    private CourseLevel courseLevel;

    private CourseDTO(CourseModel courseModel) {
        this.courseId = courseModel.getCourseId();
        this.name = courseModel.getName();
        this.description = courseModel.getDescription();
        this.imageUrl = courseModel.getImageUrl();
        this.courseStatus = courseModel.getCourseStatus();
        this.userInstructor = courseModel.getUserInstructor();
        this.courseLevel = courseModel.getCourseLevel();
    }

    public static CourseDTO converter(CourseModel courseModel) {
        return new CourseDTO(courseModel);
    }

    public static PageImpl<CourseDTO> converter(Page<CourseModel> courseEntities) {
        List<CourseDTO> courseDTOS = courseEntities.stream().map(CourseDTO::converter).collect(Collectors.toList());
        return new PageImpl<CourseDTO>(courseDTOS);
    }
}

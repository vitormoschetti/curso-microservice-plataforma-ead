package com.ead.course.application.model;

import com.ead.course.adapter.repository.entity.CourseEntity;
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

    private CourseDTO(UUID courseId, String name, String description, String imageUrl, CourseStatus courseStatus, UUID userInstructor, CourseLevel courseLevel) {
        this.courseId = courseId;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.courseStatus = courseStatus;
        this.userInstructor = userInstructor;
        this.courseLevel = courseLevel;
    }

    public static CourseDTO convert(CourseEntity e) {
        return new CourseDTO(e.getCourseId(), e.getName(), e.getDescription(), e.getImageUrl(), e.getCourseStatus(), e.getUserInstructor(), e.getCourseLevel());
    }

    public static PageImpl<CourseDTO> convert(Page<CourseEntity> courseEntities) {
        List<CourseDTO> courseDTOS = courseEntities.stream().map(CourseDTO::convert).collect(Collectors.toList());
        return new PageImpl<CourseDTO>(courseDTOS);
    }
}

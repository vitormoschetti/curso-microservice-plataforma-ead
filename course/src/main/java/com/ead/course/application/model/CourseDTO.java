package com.ead.course.application.model;

import com.ead.course.adapter.repository.entity.CourseEntity;
import com.ead.course.application.model.enums.CourseLevel;
import com.ead.course.application.model.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Page;

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

    private CourseDTO(String name, String description, String imageUrl, CourseStatus courseStatus, UUID userInstructor, CourseLevel courseLevel) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.courseStatus = courseStatus;
        this.userInstructor = userInstructor;
        this.courseLevel = courseLevel;
    }

    public static CourseDTO convert(CourseEntity e) {
        return new CourseDTO(e.getName(), e.getDescription(), e.getImageUrl(), e.getCourseStatus(), e.getUserInstructor(), e.getCourseLevel());
    }

    public static List<CourseDTO> convert(Page<CourseEntity> courseEntities) {
        return courseEntities.stream().map(CourseDTO::convert).collect(Collectors.toList());
    }
}

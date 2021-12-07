package com.ead.authuser.application.model;


import com.ead.authuser.application.model.enums.CourseLevel;
import com.ead.authuser.application.model.enums.CourseStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDTO {

    @JsonProperty("courseId")
    private UUID courseId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("imageUrl")
    private String imageUrl;
    @JsonProperty("courseStatus")
    private CourseStatus courseStatus;
    @JsonProperty("userInstructor")
    private UUID userInstructor;
    @JsonProperty("courseLevel")
    private CourseLevel courseLevel;

}

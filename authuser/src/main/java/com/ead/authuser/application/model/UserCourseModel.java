package com.ead.authuser.application.model;

import com.ead.authuser.adapter.outbound.persistence.entity.UserCourseEntity;
import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import lombok.Getter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class UserCourseModel {

    private UUID id;
    private UserModel userModel;
    private UUID courseId;

    public UserCourseModel(UserModel userModel, UUID courseId) {
        this.userModel = userModel;
        this.courseId = courseId;
    }
    public UserCourseModel(UserCourseEntity userCourseEntity) {

        UserEntity user = userCourseEntity.getUser();
        UserModel userModel = new UserModel(user);

        this.userModel = userModel;
        this.courseId = userCourseEntity.getCourseId();

    }

    public static List<UserCourseModel> convert(List<UserCourseEntity> userCourseEntities) {

        return userCourseEntities.stream().map(UserCourseModel::new).collect(Collectors.toList());

    }
}

package com.ead.authuser.application.ports.repository;

import com.ead.authuser.application.model.UserCourseModel;
import com.ead.authuser.application.model.UserModel;

import java.util.List;
import java.util.UUID;

public interface UserCourseRepositoryPort {

    boolean existsByUserAndCourseId(UserModel userModel, UUID courseId);

    void save(UserCourseModel userCourseModel);

    List<UserCourseModel> findAllByUserUserId(UUID userId);

    void deleteAll(List<UserCourseModel> userCourseModels);
}

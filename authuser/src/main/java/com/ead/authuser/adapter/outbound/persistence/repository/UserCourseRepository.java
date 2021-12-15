package com.ead.authuser.adapter.outbound.persistence.repository;

import com.ead.authuser.adapter.outbound.persistence.entity.UserCourseEntity;
import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import com.ead.authuser.application.model.UserCourseModel;
import com.ead.authuser.application.model.UserModel;
import com.ead.authuser.application.ports.repository.UserCourseRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UserCourseRepository implements UserCourseRepositoryPort {

    private final PostgresUserCourseRepository userCourseRepository;

    public UserCourseRepository(PostgresUserCourseRepository userCourseRepository) {
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public boolean existsByUserAndCourseId(UserModel userModel, UUID courseId) {
        UserEntity userEntity = new UserEntity(userModel);
        return userCourseRepository.existsByUserAndCourseId(userEntity, courseId);
    }

    @Override
    public void save(UserCourseModel userCourseModel) {
        UserCourseEntity userCourseEntity = new UserCourseEntity(userCourseModel);
        userCourseRepository.save(userCourseEntity);
    }

    @Override
    public List<UserCourseModel> findAllByUserUserId(UUID userId) {
        List<UserCourseEntity> userCourseEntities = userCourseRepository.findAllByUserUserId(userId);
        return UserCourseModel.convert(userCourseEntities);
    }

    @Override
    public void deleteAll(List<UserCourseModel> userCourseModels) {
        List<UserCourseEntity> userCourseEntities = UserCourseEntity.convert(userCourseModels);
        userCourseRepository.deleteAll(userCourseEntities);
    }
}

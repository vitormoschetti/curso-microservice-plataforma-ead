package com.ead.authuser.adapter.outbound.persistence.repository;

import com.ead.authuser.adapter.outbound.persistence.entity.UserCourseEntity;
import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostgresUserCourseRepository extends JpaRepository<UserCourseEntity, UUID> {

    boolean existsByUserAndCourseId(UserEntity userEntity, UUID courseId);

    List<UserCourseEntity> findAllByUserUserId(UUID userId);

}

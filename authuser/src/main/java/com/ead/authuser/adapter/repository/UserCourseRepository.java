package com.ead.authuser.adapter.repository;

import com.ead.authuser.adapter.repository.entity.UserCourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourseEntity, UUID> {
}

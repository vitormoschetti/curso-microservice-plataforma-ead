package com.ead.authuser.application.ports.repository;

import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import com.ead.authuser.adapter.specification.SpecificationTemplate;
import com.ead.authuser.application.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryPort {

    Page<UserModel> findAll(SpecificationTemplate.UserSpec spec, Pageable pageable);

    Page<UserModel> findAll(Specification<UserEntity> spec, Pageable pageable);

    Optional<UserModel> findById(UUID userId);

    void save(UserModel userModel);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    void delete(UserModel userEntity);

    void update(UserModel userModel);
}

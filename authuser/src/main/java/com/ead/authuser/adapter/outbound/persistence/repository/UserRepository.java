package com.ead.authuser.adapter.outbound.persistence.repository;

import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import com.ead.authuser.adapter.specification.SpecificationTemplate;
import com.ead.authuser.application.model.UserModel;
import com.ead.authuser.application.ports.repository.UserRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserRepository implements UserRepositoryPort {

    private final PostgresUserRepository userRepository;

    public UserRepository(PostgresUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<UserModel> findAll(SpecificationTemplate.UserSpec spec, Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(spec, pageable);
        return UserModel.convert(userEntities);
    }

    @Override
    public Page<UserModel> findAll(Specification<UserEntity> spec, Pageable pageable) {
        Page<UserEntity> userEntities = userRepository.findAll(spec, pageable);
        return UserModel.convert(userEntities);
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);

        if (userEntity.isEmpty())
            return Optional.empty();

        return Optional.of(new UserModel(userEntity.get()));
    }

    @Override
    public void save(UserModel userModel) {
        UserEntity userEntity = new UserEntity(userModel);
        userRepository.save(userEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public void delete(UserModel userModel) {
        UserEntity userEntity = new UserEntity().delete(userModel);
        userRepository.delete(userEntity);
    }

    @Override
    public void update(UserModel userModel) {
        UserEntity userEntity = new UserEntity().update(userModel);
        userRepository.save(userEntity);
    }
}

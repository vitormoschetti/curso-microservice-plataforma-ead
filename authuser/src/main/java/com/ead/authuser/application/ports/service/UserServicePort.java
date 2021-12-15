package com.ead.authuser.application.ports.service;

import com.ead.authuser.adapter.inbound.controller.dto.UserAuthDTO;
import com.ead.authuser.adapter.inbound.controller.dto.UserDTO;
import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import com.ead.authuser.adapter.specification.SpecificationTemplate;
import com.ead.authuser.application.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserServicePort {

    Page<UserDTO> getAllUsers(SpecificationTemplate.UserSpec spec, Pageable pageable);

    Page<UserDTO> getAllUsers(Specification<UserEntity> and, Pageable pageable);

    Optional<UserDTO> getUser(UUID userId);

    Optional<UserDTO> delete(UUID userId);

    UserDTO create(UserAuthDTO userAuthDTO);

    boolean existsUsername(String username);

    boolean existsEmail(String email);

    Optional<UserDTO> updateUser(UUID userId, UserAuthDTO userPut);

    Optional<UserDTO> updatePassword(UUID userId, UserAuthDTO passwordPut);

    Optional<UserDTO> updateImage(UUID userId, UserAuthDTO imagePut);

    Optional<UserModel> getEntity(UUID userId);
}

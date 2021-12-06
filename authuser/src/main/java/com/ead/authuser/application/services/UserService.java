package com.ead.authuser.application.services;

import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.adapter.specifications.SpecificationTemplate;
import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface UserService {

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

}

package com.ead.authuser.application.services;

import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {

    List<UserDTO> getAllUsers();

    Optional<UserDTO> getUser(UUID userId);

    Optional<UserDTO> delete(UUID userId);

    UserDTO create(UserAuthDTO userAuthDTO);

    boolean existsUsername(String username);

    boolean existsEmail(String email);

    Optional<UserDTO> updateUser(UUID userId, UserAuthDTO userPut);

    Optional<UserDTO> updatePassword(UUID userId, UserAuthDTO passwordPut);

    Optional<UserDTO> updateImage(UUID userId, UserAuthDTO imagePut);
}

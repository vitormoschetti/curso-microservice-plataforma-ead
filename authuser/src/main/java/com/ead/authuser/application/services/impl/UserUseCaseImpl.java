package com.ead.authuser.application.services.impl;

import com.ead.authuser.adapter.repository.UserRepository;
import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.UserService;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class UserUseCaseImpl implements UserService {

    //TODO testar beanUtil userEntity to userDTO

    private final UserRepository userRepository;

    public UserUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<UserDTO> getAllUsers() {

        List<UserEntity> userEntities = userRepository.findAll();

        return UserDTO.convert(userEntities);

    }

    @Override
    public Optional<UserDTO> getUser(UUID userId) {

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        return UserDTO.convert(userEntity);
    }

    @Override
    public Optional<UserDTO> delete(UUID userDTO) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO);

        if (userEntityOptional.isEmpty())
            return Optional.empty();

        userRepository.delete(userEntityOptional.get());
        return UserDTO.convert(userEntityOptional);
    }

    @Override
    public UserDTO create(UserAuthDTO userAuthDTO) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userAuthDTO, userEntity);

        userEntity.newComplete();

        userRepository.save(userEntity);

        return UserDTO.convert(userEntity);

    }


    @Override
    public boolean existsEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsUsername(String username) {
        return userRepository.existsByUsername(username);
    }
}

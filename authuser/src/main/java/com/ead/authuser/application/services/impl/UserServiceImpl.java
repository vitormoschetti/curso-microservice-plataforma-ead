package com.ead.authuser.application.services.impl;

import com.ead.authuser.adapter.repository.UserCourseRepository;
import com.ead.authuser.adapter.repository.UserRepository;
import com.ead.authuser.adapter.repository.entity.UserCourseEntity;
import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.adapter.specifications.SpecificationTemplate;
import com.ead.authuser.application.model.UserAuthDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.UserService;
import com.ead.authuser.application.services.excepitons.InvalidPasswordException;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserCourseRepository userCourseRepository;

    public UserServiceImpl(UserRepository userRepository, UserCourseRepository userCourseRepository) {
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
    }


    @Override
    public Page<UserDTO> getAllUsers(SpecificationTemplate.UserSpec spec, Pageable pageable) {

        Page<UserEntity> userEntities = userRepository.findAll(spec, pageable);

        return UserDTO.convert(userEntities);

    }

    @Override
    public Page<UserDTO> getAllUsers(Specification<UserEntity> spec, Pageable pageable) {

        Page<UserEntity> userEntities = userRepository.findAll(spec, pageable);

        return UserDTO.convert(userEntities);
    }

    @Override
    public Optional<UserDTO> getUser(UUID userId) {

        Optional<UserEntity> userEntity = userRepository.findById(userId);

        return UserDTO.convert(userEntity);
    }

    @Override
    @Transactional
    public Optional<UserDTO> delete(UUID userDTO) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userDTO);

        if (userEntityOptional.isEmpty())
            return Optional.empty();

        List<UserCourseEntity> userCourseEntities = userCourseRepository.findAllByUserUserId(userEntityOptional.get().getUserId());

        if(!userCourseEntities.isEmpty())
            userCourseRepository.deleteAll(userCourseEntities);

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

    @Override
    public Optional<UserDTO> updateUser(UUID userId, UserAuthDTO userPut) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty())
            return Optional.empty();

        UserEntity userEntity = userEntityOptional.get();
        userEntity.updateUser(userPut);

        userRepository.save(userEntity);

        return Optional.of(UserDTO.convert(userEntity));

    }

    @Override
    public Optional<UserDTO> updatePassword(UUID userId, UserAuthDTO passwordPut) throws InvalidPasswordException {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty())
            return Optional.empty();

        UserEntity userEntity = userEntityOptional.get();

        if (userEntity.invalidPassword(passwordPut))
            throw new InvalidPasswordException("Error: Mismatched old password!");

        userEntity.updatePassword(passwordPut);

        userRepository.save(userEntity);

        return Optional.of(UserDTO.convert(userEntity));
    }

    @Override
    public Optional<UserDTO> updateImage(UUID userId, UserAuthDTO imagePut) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(userId);

        if (userEntityOptional.isEmpty())
            return Optional.empty();

        UserEntity userEntity = userEntityOptional.get();

        userEntity.updateImage(imagePut);

        userRepository.save(userEntity);

        return Optional.of(UserDTO.convert(userEntity));
    }

    @Override
    public Optional<UserEntity> getEntity(UUID userId) {
        return userRepository.findById(userId);
    }

}

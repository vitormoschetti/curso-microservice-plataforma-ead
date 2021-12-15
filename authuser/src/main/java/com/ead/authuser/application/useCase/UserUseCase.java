package com.ead.authuser.application.useCase;

import com.ead.authuser.adapter.inbound.controller.dto.UserAuthDTO;
import com.ead.authuser.adapter.inbound.controller.dto.UserDTO;
import com.ead.authuser.adapter.outbound.persistence.entity.UserCourseEntity;
import com.ead.authuser.adapter.outbound.persistence.entity.UserEntity;
import com.ead.authuser.adapter.outbound.persistence.repository.UserRepository;
import com.ead.authuser.adapter.specification.SpecificationTemplate;
import com.ead.authuser.application.model.UserCourseModel;
import com.ead.authuser.application.model.UserModel;
import com.ead.authuser.application.model.excepitons.InvalidPasswordException;
import com.ead.authuser.application.ports.repository.UserCourseRepositoryPort;
import com.ead.authuser.application.ports.repository.UserRepositoryPort;
import com.ead.authuser.application.ports.service.UserServicePort;
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
public class UserUseCase implements UserServicePort {

    private final UserRepositoryPort userRepository;
    private final UserCourseRepositoryPort userCourseRepository;

    public UserUseCase(UserRepositoryPort userRepository, UserCourseRepositoryPort userCourseRepository) {
        this.userRepository = userRepository;
        this.userCourseRepository = userCourseRepository;
    }

    @Override
    public Page<UserDTO> getAllUsers(SpecificationTemplate.UserSpec spec, Pageable pageable) {

        Page<UserModel> userModels = userRepository.findAll(spec, pageable);

        return UserDTO.convert(userModels);

    }

    @Override
    public Page<UserDTO> getAllUsers(Specification<UserEntity> spec, Pageable pageable) {

        Page<UserModel> userModels = userRepository.findAll(spec, pageable);

        return UserDTO.convert(userModels);
    }

    @Override
    public Optional<UserDTO> getUser(UUID userId) {

        Optional<UserModel> userModel = userRepository.findById(userId);

        return UserDTO.convert(userModel);
    }

    @Override
    @Transactional
    public Optional<UserDTO> delete(UUID userDTO) {

        Optional<UserModel> userModelOptional = userRepository.findById(userDTO);

        if (userModelOptional.isEmpty())
            return Optional.empty();

        List<UserCourseModel> userCourseModels = userCourseRepository.findAllByUserUserId(userModelOptional.get().getUserId());

        if(!userCourseModels.isEmpty())
            userCourseRepository.deleteAll(userCourseModels);

        userRepository.delete(userModelOptional.get());
        return UserDTO.convert(userModelOptional);
    }

    @Override
    public UserDTO create(UserAuthDTO userAuthDTO) {

        UserModel userModel = new UserModel(userAuthDTO);

        userRepository.save(userModel);

        return UserDTO.convert(userModel);

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

        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isEmpty())
            return Optional.empty();

        UserModel userModel = userModelOptional.get();
        userModel.updateUser(userPut);

        userRepository.update(userModel);

        return Optional.of(UserDTO.convert(userModel));

    }

    @Override
    public Optional<UserDTO> updatePassword(UUID userId, UserAuthDTO passwordPut) throws InvalidPasswordException {

        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isEmpty())
            return Optional.empty();

        UserModel userModel = userModelOptional.get();

        if (userModel.invalidPassword(passwordPut))
            throw new InvalidPasswordException("Error: Mismatched old password!");

        userModel.updatePassword(passwordPut);

        userRepository.update(userModel);

        return Optional.of(UserDTO.convert(userModel));
    }

    @Override
    public Optional<UserDTO> updateImage(UUID userId, UserAuthDTO imagePut) {

        Optional<UserModel> userModelOptional = userRepository.findById(userId);

        if (userModelOptional.isEmpty())
            return Optional.empty();

        UserModel userModel = userModelOptional.get();

        userModel.updateImage(imagePut);

        userRepository.update(userModel);

        return Optional.of(UserDTO.convert(userModel));
    }

    @Override
    public Optional<UserModel> getEntity(UUID userId) {
        return userRepository.findById(userId);
    }

}

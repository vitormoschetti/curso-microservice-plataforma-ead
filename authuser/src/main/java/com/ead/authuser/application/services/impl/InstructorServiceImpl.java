package com.ead.authuser.application.services.impl;

import com.ead.authuser.adapter.repository.UserRepository;
import com.ead.authuser.adapter.repository.entity.UserEntity;
import com.ead.authuser.application.model.InstructorDTO;
import com.ead.authuser.application.model.UserDTO;
import com.ead.authuser.application.services.InstructorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserDTO> subscription(InstructorDTO instructorDTO) {

        Optional<UserEntity> userEntityOptional = userRepository.findById(instructorDTO.getUserId());

        if(userEntityOptional.isEmpty())
            return Optional.empty();

        UserEntity userEntity = userEntityOptional.get();
        userEntity.promoveParaInstrutor();
        userRepository.save(userEntity);

        return Optional.of(UserDTO.convert(userEntity));
    }

}

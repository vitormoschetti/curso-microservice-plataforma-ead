package com.ead.authuser.application.useCase;

import com.ead.authuser.adapter.inbound.controller.dto.InstructorDTO;
import com.ead.authuser.adapter.inbound.controller.dto.UserDTO;
import com.ead.authuser.application.model.UserModel;
import com.ead.authuser.application.ports.repository.UserRepositoryPort;
import com.ead.authuser.application.ports.service.InstructorServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InstructorUseCase implements InstructorServicePort {

    private final UserRepositoryPort userRepository;

    @Override
    public Optional<UserDTO> subscription(InstructorDTO instructorDTO) {

        Optional<UserModel> userModelOptional = userRepository.findById(instructorDTO.getUserId());

        if (userModelOptional.isEmpty())
            return Optional.empty();

        UserModel userModel = userModelOptional.get();
        userModel.promoveParaInstrutor();
        userRepository.update(userModel);

        return Optional.of(UserDTO.convert(userModel));
    }

}

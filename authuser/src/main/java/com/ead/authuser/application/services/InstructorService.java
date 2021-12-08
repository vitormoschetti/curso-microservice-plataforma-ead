package com.ead.authuser.application.services;

import com.ead.authuser.application.model.InstructorDTO;
import com.ead.authuser.application.model.UserDTO;

import java.util.Optional;

public interface InstructorService {
    Optional<UserDTO> subscription(InstructorDTO instructorDTO);
}

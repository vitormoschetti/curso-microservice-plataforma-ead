package com.ead.authuser.application.ports.service;

import com.ead.authuser.adapter.inbound.controller.dto.InstructorDTO;
import com.ead.authuser.adapter.inbound.controller.dto.UserDTO;

import java.util.Optional;

public interface InstructorServicePort {
    Optional<UserDTO> subscription(InstructorDTO instructorDTO);
}

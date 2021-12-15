package com.ead.course.adapter.inbound.controller.dto.validation;

import com.ead.course.adapter.inbound.controller.dto.CourseDTO;
import com.ead.course.adapter.inbound.controller.dto.UserDTO;
import com.ead.course.adapter.outbound.clients.AuthUserClientFeign;
import com.ead.course.application.model.enums.UserType;
import com.ead.course.application.ports.service.CourseUserServicePort;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;


@Component
public class CursoValidator implements Validator {

    private final Validator validator;
    private final CourseUserServicePort courseUserServicePort;

    public CursoValidator(@Qualifier("defaultValidator") Validator validator, CourseUserServicePort courseUserServicePort) {
        this.validator = validator;
        this.courseUserServicePort = courseUserServicePort;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseDTO courseDTO = (CourseDTO) o;
        validator.validate(courseDTO, errors);

        if(!errors.hasErrors())
            validateUserInstructor(courseDTO.getUserInstructor(), errors);
    }

    private void validateUserInstructor(UUID userInstructor, Errors errors) {
        UserDTO userDTO = courseUserServicePort.getOneUserByUserId(userInstructor);

        if(userDTO == null) {
            errors.rejectValue("userInstructor", "UserInstructorError", "Instrutor não encontrado");
        }

        if(userDTO.getUserType().equals(UserType.STUDENT))
            errors.rejectValue("userInstructor", "UserInstructorError", "Usuário deve ser INSTRUTOR ou ADMINISTRADOR");

    }
}

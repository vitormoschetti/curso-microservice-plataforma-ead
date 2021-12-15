package com.ead.authuser.adapter.inbound.controller.dto.validation.impl;

import com.ead.authuser.adapter.inbound.controller.dto.validation.CPFConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CPFConstraintImpl implements ConstraintValidator<CPFConstraint, String> {
    @Override
    public void initialize(CPFConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String cpf, ConstraintValidatorContext context) {

        return cpf.matches("([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})");
    }
}

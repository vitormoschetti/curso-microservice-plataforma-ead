package com.ead.authuser.adapter.inbound.controller.dto.validation.impl;

import com.ead.authuser.adapter.inbound.controller.dto.validation.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameConstraintImpl implements ConstraintValidator<UsernameConstraint, String> {

    @Override
    public void initialize(UsernameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {

        if (username == null || username.trim().isEmpty() || username.contains(" "))
            return false;

        return true;
    }
}

package com.ead.authuser.adapter.inbound.controller.dto.validation;

import com.ead.authuser.adapter.inbound.controller.dto.validation.impl.CPFConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = CPFConstraintImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface CPFConstraint {

    String message() default "CPF invalid! The format must be xxx.xxx.xxx-xx";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

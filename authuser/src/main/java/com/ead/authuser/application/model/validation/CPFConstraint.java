package com.ead.authuser.application.model.validation;

import com.ead.authuser.application.model.validation.impl.CPFConstraintImpl;
import com.ead.authuser.application.model.validation.impl.PhoneNumberConstraintImpl;

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

package com.ead.authuser.application.model.validation;

import com.ead.authuser.application.model.validation.impl.UsernameConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = UsernameConstraintImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {

    String message() default "Username invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

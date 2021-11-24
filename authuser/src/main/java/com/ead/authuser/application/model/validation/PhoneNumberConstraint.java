package com.ead.authuser.application.model.validation;


import com.ead.authuser.application.model.validation.impl.PhoneNumberConstraintImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = PhoneNumberConstraintImpl.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberConstraint {

    String message() default "Phone invalid! The format must be (xx) xxxxx-xxxx";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}


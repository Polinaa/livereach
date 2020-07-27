package com.example.livereach.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = TimeRangeValidatorImpl.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeRangeValidator {

    String message() default "Invalid time range. Min time must be smaller than max time and the time between them not\n"
            + "greater than a month.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

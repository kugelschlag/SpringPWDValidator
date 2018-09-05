package com.kugelschlag.SpringPwdValidator.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


/**
 * Custom annotation
 * Use "@CharterPWValid" on data object fields to apply the validation handling implemented
 * in CharterPWValidator
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE, PARAMETER})
@Constraint(validatedBy = CharterPWValidator.class)
public @interface CharterPWValid {
    String message() default "Password Validation Error";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

package org.springframeworkcore.mvc.javaannotationbased.validators.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframeworkcore.mvc.javaannotationbased.validators.implementations.NoSpacesValidator;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = NoSpacesValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoSpaces {
    String message() default "No spaces are allowed inside field";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

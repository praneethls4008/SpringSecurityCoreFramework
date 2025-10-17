package org.springframeworkcore.mvc.javaannotationbased.validators.implementations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframeworkcore.mvc.javaannotationbased.validators.annotations.NoSpaces;

public class NoSpacesValidator implements ConstraintValidator<NoSpaces, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return !value.contains(" ");
    }
}

package com.example.proyectospring.validators.api;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DniValidator implements ConstraintValidator<Dni, String> {
    @Override
    public void initialize(Dni constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        Pattern pattern = Pattern.compile("^[0-9]{8}[TRWAGMYFPDXBNJZSQVHLCKE]$");
        Matcher matcher = pattern.matcher(s);
        return matcher.matches();
    }
}

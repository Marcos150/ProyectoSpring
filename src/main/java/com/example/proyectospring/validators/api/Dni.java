package com.example.proyectospring.validators.api;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = DniValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented


public @interface Dni {
    String message() default "{mob.concept.admin.models.constraint.Dni.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

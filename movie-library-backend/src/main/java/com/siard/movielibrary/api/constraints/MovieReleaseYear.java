package com.siard.movielibrary.api.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = MovieReleaseYearValidator.class)
@Target({ FIELD, PARAMETER })
@Retention(RUNTIME)
@Documented
public @interface MovieReleaseYear {
    public String message() default "Invalid release year";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};
}

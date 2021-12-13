package com.siard.movielibrary.api.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Year;

public class MovieReleaseYearValidator implements ConstraintValidator<MovieReleaseYear, Short> {
    @Override
    public boolean isValid(Short releaseYear, ConstraintValidatorContext constraintValidatorContext) {
        return releaseYear >= 1888 && releaseYear <= Year.now().getValue();
    }
}

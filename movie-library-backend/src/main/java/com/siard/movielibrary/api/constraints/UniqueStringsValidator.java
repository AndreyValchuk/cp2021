package com.siard.movielibrary.api.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class UniqueStringsValidator implements ConstraintValidator<UniqueStrings, Collection<String>> {
    @Override
    public boolean isValid(Collection<String> values, ConstraintValidatorContext constraintValidatorContext) {
        return values.stream().map(String::toLowerCase).distinct().count() == values.size();
    }
}
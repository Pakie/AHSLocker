package com.pakie.ahslocker.web.custom_errors;

import com.pakie.ahslocker.web.UserDto;
import com.pakie.ahslocker.web.UserRegistrationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof UserDto user) {
            return user.getPassword() != null && user.getPassword().equals(user.getConfirmPassword());
        }
        if (obj instanceof UserRegistrationDto reg) {
            return reg.getPassword() != null && reg.getPassword().equals(reg.getConfirmPassword());
        }
        return true;
    }
}
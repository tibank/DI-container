package com.epam.rd.spring2019.hwspring01.validators;

import com.epam.rd.spring2019.hwspring01.exceptions.ValidationException;

public class UserValidatorImpl implements UserValidator {

    @Override
    public void validateUserCredentials(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            throw new ValidationException("Invalid credentials: e-mail or/and password is empty!");
        }
    }

}

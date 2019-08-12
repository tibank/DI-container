package com.epam.rd.spring2019.hwspring01.validators;

import com.epam.rd.spring2019.hwspring01.exceptions.ValidationException;

public interface UserValidator {

    void validateUserCredentials(String email, String password) throws ValidationException;
}

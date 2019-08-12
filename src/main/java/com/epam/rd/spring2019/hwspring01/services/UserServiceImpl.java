package com.epam.rd.spring2019.hwspring01.services;

import com.epam.rd.spring2019.hwspring01.daos.UserDao;
import com.epam.rd.spring2019.hwspring01.exceptions.ValidationException;
import com.epam.rd.spring2019.hwspring01.models.User;
import com.epam.rd.spring2019.hwspring01.validators.UserValidator;

import java.io.IOException;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final SecurityService securityService;
    private final UserValidator userValidator;

    public UserServiceImpl(UserDao userDao, SecurityService securityService, UserValidator userValidator) {
        this.userDao = userDao;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }

    @Override
    public void login(String email, String password) throws ClassNotFoundException, IOException {
        userValidator.validateUserCredentials(email, password);
        User user = userDao.getUserByEmail(email);
        if (!securityService.isCorrectPassword(user, password)) {
            throw new ValidationException("Wrong password");
        }
    }
}

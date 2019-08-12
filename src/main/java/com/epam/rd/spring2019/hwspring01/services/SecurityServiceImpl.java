package com.epam.rd.spring2019.hwspring01.services;


import com.epam.rd.spring2019.hwspring01.models.User;

public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean isCorrectPassword(User user, String password) {
        return user.password.equals(password);
    }
}

package com.epam.rd.spring2019.hwspring01.services;

import com.epam.rd.spring2019.hwspring01.models.User;

public interface SecurityService {
    boolean isCorrectPassword(User user, String password);
}

package com.epam.rd.spring2019.hwspring01.services;

import java.io.IOException;

public interface UserService {
    void login(String email, String password) throws ClassNotFoundException, IOException;
}

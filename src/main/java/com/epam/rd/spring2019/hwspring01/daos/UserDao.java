package com.epam.rd.spring2019.hwspring01.daos;


import com.epam.rd.spring2019.hwspring01.models.User;

import java.io.IOException;

public interface UserDao {

    User getUserByEmail(String email) throws ClassNotFoundException, IOException;

}

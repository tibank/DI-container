package com.epam.rd.spring2019.hwspring01.daos;

import com.epam.rd.spring2019.hwspring01.container.DiContainerImpl;
import com.epam.rd.spring2019.hwspring01.models.User;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class UserDaoImpl implements UserDao {

    @Override
    public User getUserByEmail(String email) throws ClassNotFoundException, IOException {
        DiContainerImpl container = new DiContainerImpl("context.ini");

        return (User) container.getInstance(User.class);
    }
}

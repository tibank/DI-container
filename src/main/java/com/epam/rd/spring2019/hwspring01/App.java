package com.epam.rd.spring2019.hwspring01;

import com.epam.rd.spring2019.hwspring01.container.DiContainerImpl;
import com.epam.rd.spring2019.hwspring01.services.UserService;
import com.epam.rd.spring2019.hwspring01.services.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) {
        String configFile = "context.ini";
        if (args.length > 0) {
            configFile = args[0];
        }

        try {
            DiContainerImpl container = new DiContainerImpl(configFile);

            UserService userService = (UserService) container.getInstance(UserServiceImpl.class);
            userService.login("admin@admin.com","1q2w3e4r");
            log.info("login 'admin@admin.com' is correct");
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
        }

    }
}

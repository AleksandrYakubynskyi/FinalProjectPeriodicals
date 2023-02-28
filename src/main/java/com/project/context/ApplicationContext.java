package com.project.context;

import com.project.dao.PublicationDao;
import com.project.dao.UserDao;
import com.project.dao.impl.DefaultPublicationDao;
import com.project.dao.impl.DefaultUserDao;
import com.project.service.DefaultPublicationService;
import com.project.service.PublicationService;
import com.project.service.DefaultUserService;
import com.project.service.UserService;

public class ApplicationContext {
    private static ApplicationContext instance;
    private final PublicationDao publicationDao = new DefaultPublicationDao();
    private final UserDao userDao = new DefaultUserDao();
    private final PublicationService publicationService = new DefaultPublicationService(publicationDao);
    private final UserService userService = new DefaultUserService(userDao);

    private ApplicationContext() {
    }

    public static ApplicationContext getContext() {
        if (instance == null) {
            instance = new ApplicationContext();
        }
        return instance;
    }

    public PublicationDao getPublicationDao() {
        return publicationDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public PublicationService getPublicationService() {
        return publicationService;
    }

    public UserService getUserService() {
        return userService;
    }
}

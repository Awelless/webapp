package com.epam.web.service;

import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.UserDao;
import com.epam.web.entity.User;

import java.util.Optional;

public class UserService {

    private final DaoHelperFactory daoHelperFactory;

    public UserService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public Optional<User> login(String username, String password) throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();

            return userDao.findByUsernameAndPassword(username, password);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public void create(String username, String password) throws ServiceException {

        User user = new User(username, password);

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();

            userDao.save(user);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Optional<User> getByUsername(String username) throws ServiceException {

        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            UserDao userDao = daoHelper.createUserDao();

            return userDao.findByUsername(username);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }
}

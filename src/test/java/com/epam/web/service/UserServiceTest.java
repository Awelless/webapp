package com.epam.web.service;

import com.epam.web.dao.DaoException;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.UserDao;
import com.epam.web.entity.User;
import com.epam.web.entity.UserRole;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

import static org.mockito.Matchers.any;

public class UserServiceTest {

    private final UserDao userDao = Mockito.mock(UserDao.class);
    private final DaoHelper daoHelper = Mockito.mock(DaoHelper.class);
    private final DaoHelperFactory daoHelperFactory = Mockito.mock(DaoHelperFactory.class);

    private final UserService service = new UserService(daoHelperFactory);

    @Before
    public void init() throws DaoException, SQLException {
        User user = new User(1L, "123", "123", UserRole.USER);

        Mockito.doNothing().when(userDao).save(any(User.class));
        Mockito.when(userDao.findByUsernameAndPassword("123", "123")).thenReturn(Optional.of(user));

        Mockito.when(daoHelperFactory.create()).thenReturn(daoHelper);
        Mockito.when(daoHelper.createUserDao()).thenReturn(userDao);
        Mockito.doNothing().when(daoHelper).beginTransaction();
        Mockito.doNothing().when(daoHelper).endTransaction();
        Mockito.doNothing().when(daoHelper).commit();
        Mockito.doNothing().when(daoHelper).close();
        Mockito.doNothing().when(daoHelper).rollback();
    }

    @Test
    public void testCreate() throws ServiceException, DaoException {

        service.create("123", "qwerty");

        Mockito.verify(userDao, Mockito.times(1)).save(any(User.class));
    }

    @Test
    public void testLogin() throws ServiceException, DaoException {

        service.login("123", "123");

        Mockito.verify(userDao, Mockito.times(1)).findByUsernameAndPassword(
                "123", "123");
    }
}

package com.revature.planetarium.service.user;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.repository.user.UserDaoImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;

public class AuthUserServicePositiveTest {

    private UserDao userDAO;
    private UserService userService;

    private User positiveUser;
    private User stubbedUser;

    public String username = "Batman";
    public String password = "Iamthenight1939";

    @Before
    public void setup(){
        userDAO = Mockito.mock(UserDaoImp.class);
        userService = new UserServiceImp(userDAO);
        positiveUser = new User(0,username,password);
        stubbedUser = new User(1,username,null);
    }

    @Test
    public void authUserServicePositiveTest() throws SQLException {
        Mockito.when(userDAO.findUserByUsername(positiveUser.getUsername())).thenReturn(Optional.of(positiveUser));
        User result = userService.authenticate(positiveUser);
        Assert.assertEquals(stubbedUser, result);
    }

}

package com.revature.planetarium.service.user;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
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

@RunWith(Parameterized.class)
public class AuthUserServiceNegativeTest {

    private UserDao userDAO;
    private UserService userService;

    private User negativeUser;

    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String message;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][] {
                {"Spiderman","Withgreatpower1962","Invalid credentials"},
                {"Batman","Imthejokerbaby","Invalid credentials"}
        };
    }

    @Before
    public void setup(){
        userDAO = Mockito.mock(UserDaoImp.class);
        userService = new UserServiceImp(userDAO);
        negativeUser = new User(0,username,password);
    }

    @Test
    public void authUserServicePositiveTest() throws SQLException {
        Mockito.when(userDAO.findUserByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        Mockito.when(userDAO.findUserByUsername("Batman")).thenReturn(Optional.of(new User(1,"Batman","Iamthenight1939")));
        UserFail exception = Assert.assertThrows(UserFail.class, () -> userService.authenticate(negativeUser));
        Assert.assertEquals(message,exception.getMessage());
    }
}

package com.revature.planetarium.service.user;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.repository.user.UserDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(Parameterized.class)
public class CreateUserServNegativeTest {
    private UserDao userDao;
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
        return new String[][]{
                {"Batman","bObb1","Invalid username"},
                {"bobb","bObb1","Invalid username"},
                {"thisisoverthirtycharachtersssss","bObb1","Invalid username"},
                {"Robin>!@#?!@#$%","bObb1","Invalid username"},
                {"_Robin","bObb1","Invalid username"},
                {"3Robin","b0bb1","Invalid username"},
                {"-Robin","b0bb1","Invalid username"},
                {"Robin","Bob3","Invalid password"},
                {"Robin","bobby","Invalid password"},
                {"Robin","Bobb3!@%!@?$%","Invalid password"},
                {"Robin","3obbY","Invalid password"},
                {"Robin","ThisisoverthirtyCharacters3ss3s","Invalid password"},
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userDao = Mockito.mock(UserDaoImp.class);
        userService = new UserServiceImp(userDao);
        negativeUser = new User (0, username, password);
    }

    @Test
    public void createUserServiceNegativeTest() throws SQLException {
        Mockito.when(userDao.createUser(Mockito.any())).thenThrow(new AssertionError(
                "createUser should not have been reached"
        ));
        Mockito.when(userDao.findUserByUsername("Batman")).thenReturn(Optional.of(new User(1, "Batman", "Iamthenight1939")));
        Mockito.when(userDao.findUserByUsername(Mockito.anyString())).thenReturn(Optional.empty());
        UserFail exception = Assert.assertThrows(UserFail.class, () -> userService.createUser(negativeUser));
        Assert.assertEquals(message, exception.getMessage());
    }
}

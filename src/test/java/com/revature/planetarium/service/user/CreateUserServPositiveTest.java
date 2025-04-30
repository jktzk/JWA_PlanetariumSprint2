package com.revature.planetarium.service.user;

import com.revature.planetarium.entities.User;
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
public class CreateUserServPositiveTest {

    private UserDao userDao;
    private UserService userService;

    private User positiveUser;
    private User stubbedUser;

    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][] {
                {"Robin","bObb1"},
                {"bobby","bObb1"},
                {"Ro_3-5","bObb1"},
                {"thisshouldbethirtycharachterts","bObb1"},
                {"Robin","bob_b1"},
                {"Robin","bobb-1"},
                {"Robin","bob_b-1"},
                {"Robin","Thisshouldbethirtycharcters3"},
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userDao = Mockito.mock(UserDaoImp.class);
        userService = new UserServiceImp(userDao);
        positiveUser = new User (0, username, password);
        stubbedUser = new User(3, username, password);
    }

    @Test
    public void createUserServicePositiveTest() throws SQLException {
        Mockito.when(userDao.findUserByUsername(positiveUser.getUsername())).thenReturn(Optional.empty());
        Mockito.when(userDao.createUser(positiveUser)).thenReturn(Optional.of(stubbedUser));
        String result = userService.createUser(positiveUser);
        Assert.assertEquals("User created successfully", result);
    }
}

package com.revature.planetarium.repository.user;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(Parameterized.class)
public class RetrievalUserRepoPositiveTest {

    private UserDao userDao;
    private String userUsername;

    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"Batman"},
                {"Superman"}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userDao = new UserDaoImp();
        userUsername = username;
    }

    @Test
    public void retrievalUserPositiveTest() throws SQLException {
        Optional<User> result = userDao.findUserByUsername(userUsername);
        Assert.assertTrue(result.isPresent());
        User returnedUser = result.get();
        Assert.assertEquals(userUsername,returnedUser.getUsername());
    }


}

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
public class RetrievalUserRepoNegativeTest {

    private UserDao userDao;
    private String userUsername;

    @Parameterized.Parameter
    public String username;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"Robin"},
                {"asdasd"}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userDao = new UserDaoImp();
        userUsername = username;
    }

    @Test
    public void retrievalUserNegativeTest() throws SQLException {
        Optional<User> result = userDao.findUserByUsername(userUsername);
        Assert.assertTrue(result.isEmpty());
    }













}

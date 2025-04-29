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
public class CreateUserRepoPositiveTest {


    private UserDao userDao;

    private User postitiveUser;


    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;

    @Parameterized.Parameters
    public static String[][] inputs(){
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
        userDao = new UserDaoImp();
        postitiveUser = new User(0,username,password);
    }

    @Test
    public void createUserPositiveTest() throws SQLException {
        Optional<User> result = userDao.createUser(postitiveUser);
        Assert.assertTrue(result.isPresent());
        User returnedUser = result.get();
        // here we check that the user was assigned id
        Assert.assertTrue(returnedUser.getId() > 0);
    }






}

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
@RunWith(Parameterized.class)
public class CreateUserRepoNegativeTest {

    private UserDao userDao;

    private User negativeUser;

    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String constraint;
    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][]{
                {"Batman","bObb1","UNIQUE"},
                {"bobb","bObb1","username_length_check"},
                {"thisisoverthirtycharachtersssss","bObb1","username_length_check"},
                {"Robin>!@#?!@#$%","bObb1","username_character_check"},
                {"_Robin","bObb1","username_character_check"},
                {"3Robin","b0bb1","username_character_check"},
                {"-Robin","b0bb1","username_character_check"},
                {"Robin","Bob3","password_length_check"},
                {"Robin","bobby","password_character_check"},
                {"Robin","Bobb3!@%!@?$%","password_character_check"},
                {"Robin","3obbY","password_character_check"},
                {"Robin","ThisisoverthirtyCharacters3ss3s","password_length_check"},
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        userDao = new UserDaoImp();
        negativeUser = new User(0, username, password);
    }

    @Test
    public void createUserNegativeTest() {
        SQLException result = Assert.assertThrows(SQLException.class, () -> {
            userDao.createUser(negativeUser);
        });
//        System.out.println(result.getMessage());
        Assert.assertTrue(result.getMessage().contains(constraint));
    }


}

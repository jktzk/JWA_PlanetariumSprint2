package com.revature.planetarium.repository.user;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
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
public class MoonDeletionPositiveTest {


        private MoonDao moonDao;



    @Parameterized.Parameter
        public String moonName;


        @Parameterized.Parameters
        public static String[][] inputs(){
            return new String[][] {
                    {"deimos"},
                    {"Khonsu"},
                    {"PHOBOS"},
                    {"_e_u_r_o_p_a_"},
                    {"t i t a n"},
                    {"-c-a-l-l-i-s-t-o"},
                    {"1tr1t0n"},
                    {"ch4 R-0_N"},
            };
        }

        @Before
        public void setup() throws IOException, InterruptedException, SQLException {
            TestUtilities.resetDatabase();
            moonDao = new MoonDaoImp();
            Moon moonToDelete = new Moon(0, moonName, 1);
            moonDao.createMoon(moonToDelete);
        }

        @Test
        public void deleteMoonPositiveTest() throws SQLException {
            boolean deleted = moonDao.deleteMoon(moonName);
            Assert.assertTrue(moonName, deleted);
            Optional<Moon> result = moonDao.readMoon(moonName);
            Assert.assertFalse(moonName, result.isPresent());
        }
}

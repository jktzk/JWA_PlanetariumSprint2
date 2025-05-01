package com.revature.planetarium.repository.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
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
public class MoonCreationPositiveTest {

    private MoonDao moonDao;

    private Moon positiveMoon;

    public static String pathName = "src/test/resources/Celestial-Images/";
    @Parameterized.Parameter
    public String moonName;
    @Parameterized.Parameter(1)
    public String ownerId;
    @Parameterized.Parameter(2)
    public String imageData;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][] {
                {"EarthMoon","1",""},
                {"EarthMoon","1","moon-1.jpg"},
                {"A","1",""},
                {"thisshouldbethirtycharachterss","1",""},
                {"deimos","1",""},
                {"PHOBOS","1",""},
                {"_e_u_r_o_p_a_","1",""},
                {" t i t a n ","1",""},
                {"-c-a-l-l-i-s-t-o-","1",""},
                {"1tr1t0n","1",""},
                {"ch4 R-0_N","1",""}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
        if (imageData.isEmpty()) {
            positiveMoon = new Moon(0,moonName,Integer.parseInt(ownerId));
        } else {
            positiveMoon = new Moon(0,moonName,Integer.parseInt(ownerId),(pathName + imageData).getBytes());
        }
    }

    @Test
    public void createPlanetRepoPositiveTest() throws SQLException {
        Optional<Moon> result = moonDao.createMoon(positiveMoon);
        Assert.assertTrue(result.isPresent());
        Moon returnedMoon = result.get();
        // here we check that the user was assigned id
        Assert.assertTrue(returnedMoon.getMoonId() > 0);
    }

}

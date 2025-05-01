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

@RunWith(Parameterized.class)
public class MoonCreationNegativeTest {

    private MoonDao moonDao;

    private Moon negativeMoon;

    public static String pathName = "src/test/resources/Celestial-Images/";
    @Parameterized.Parameter
    public String moonName;
    @Parameterized.Parameter(1)
    public String ownerId;
    @Parameterized.Parameter(2)
    public String imageData;
    @Parameterized.Parameter(3)
    public String constraint;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][] {
                {"Luna","1","","UNIQUE"},
                {"","1","","name_length_check"},
                {"thisisoverthirtycharachtersssss","1","","name_length_check"},
                {"E!@#$%^&*()_+{}|?/","1","","name_character_check"}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
        if (imageData.isEmpty()) {
            negativeMoon = new Moon(0,moonName,Integer.parseInt(ownerId));
        } else {
            negativeMoon = new Moon(0,moonName,Integer.parseInt(ownerId),(pathName + imageData).getBytes());
        }
    }

    @Test
    public void createPlanetRepoNegativeTest() throws SQLException {
        SQLException result = Assert.assertThrows(SQLException.class, () -> moonDao.createMoon(negativeMoon));
        Assert.assertTrue(result.getMessage().contains(constraint));
    }

}

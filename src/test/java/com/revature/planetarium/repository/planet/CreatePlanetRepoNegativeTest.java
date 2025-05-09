package com.revature.planetarium.repository.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.SQLException;


@RunWith(Parameterized.class)
public class CreatePlanetRepoNegativeTest {
    private PlanetDao planetDao;

    private Planet negativePlanet;

    @Parameterized.Parameter
    public String planetName;
    @Parameterized.Parameter(1)
    public String ownerId;
    @Parameterized.Parameter(2)
    public String constraint;

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][] {
                {"Earth","1","UNIQUE"},
                {"","1","name_length_check"},
                {"thisisoverthirtycharachtersssss","1","name_length_check"},
                {"E!@#$%^&*()_+{}|?/","1","name_character_check"}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
        negativePlanet = new Planet(0, planetName, Integer.parseInt(ownerId));
    }

    @Test
    public void createPlanetRepoNegativeTest() throws SQLException {
        SQLException result = Assert.assertThrows(SQLException.class, () -> planetDao.createPlanet(negativePlanet));
        Assert.assertTrue(result.getMessage().contains(constraint));
    }
}

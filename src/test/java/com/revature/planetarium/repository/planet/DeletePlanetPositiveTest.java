package com.revature.planetarium.repository.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.util.TestUtilities;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.*;

import java.io.IOException;
import java.sql.SQLException;

import java.io.IOException;
import java.sql.SQLException;
@RunWith(Parameterized.class)
public class DeletePlanetPositiveTest {


    private PlanetDao planetDAO;
    private Planet posotivePlanet;

    @Parameterized.Parameter
    public String planetName;

    @Parameterized.Parameters
    public static String[][] inputs(){
        return new String[][] {
                {"Mars"},
                {"Earth"}

        };
    }

    @Before
    public void setup() throws IOException, InterruptedException{
        TestUtilities.resetDatabase();
        planetDAO = new PlanetDaoImp();
        posotivePlanet = new Planet(0,planetName,0);

    }



    @Test
    public void deletePlanetPositiveTest() throws SQLException {
        boolean result = planetDAO.deletePlanet(posotivePlanet.getPlanetName());
        Assert.assertTrue(result);

    }
}

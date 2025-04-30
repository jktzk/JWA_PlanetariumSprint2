package com.revature.planetarium.repository.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.util.TestUtilities;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RunWith(Parameterized.class)
public class DeletePlanetNegativeTest {

    private PlanetDao planetDAO;
    private Planet negativePlanet;

    @Parameterized.Parameter
    public String planetName;

    @Parameterized.Parameters
    public static String[][] inputs(){
        return new String[][] {
                {"NickleBackPlanet"},
                {"ScoobyDooPlanet"}

        };
    }

    @Before
    public void setup() throws IOException, InterruptedException{
        TestUtilities.resetDatabase();
        planetDAO = new PlanetDaoImp();
        negativePlanet = new Planet(0,planetName,0);

        }



    @Test
    public void deletePlanetNegativeTest() throws SQLException {
        boolean result = planetDAO.deletePlanet(negativePlanet.getPlanetName());
        Assert.assertFalse(result);

    }
}

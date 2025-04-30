package com.revature.planetarium.repository.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.repository.user.UserDaoImp;
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
public class CreatePlanetRepoPositiveTest {

    private PlanetDao planetDao;

    private Planet positivePlanet;

    @Parameterized.Parameter
    public String planetName;
    @Parameterized.Parameter(1)
    public int ownerId;
    @Parameterized.Parameter(2)
    public String imageData;
    // use Postman to see what this would look like with a GET method.

    public static String[][] inputs() {
        return new String[][] {
                {"Mars","1",""}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
        positivePlanet = new Planet(0,planetName,ownerId,imageData.getBytes());
    }

    @Test
    public void createUserPositiveTest() throws SQLException {
        Optional<Planet> result = planetDao.createPlanet(positivePlanet);
        Assert.assertTrue(result.isPresent());
        Planet returnedPlanet = result.get();
        // here we check that the user was assigned id
        Assert.assertTrue(returnedPlanet.getPlanetId() > 0);
    }
}

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

    public static String pathName = "src/test/resources/Celestial-Images/";
    @Parameterized.Parameter
    public String planetName;
    @Parameterized.Parameter(1)
    public String ownerId;
    @Parameterized.Parameter(2)
    public String imageData;
    // use Postman to see what this would look like with a GET method.

    @Parameterized.Parameters
    public static String[][] inputs() {
        return new String[][] {
                {"Saturn","1",""},
                {"Saturn","1","planet-5.jpg"},
                {"Saturn","1","planet-5.png"},
                {"A","1",""},
                {"jupiter","1",""},
                {"NEPTUNE","1",""},
                {" v e n u s ","1",""},
                {"-u-r-a-n-u-s-","1",""},
                {"1plu20","1",""},
                {"E 4_r-tH","1",""},
                {"thisshouldbethirtycharachterss","1",""}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
        if (imageData.isEmpty()) {
            positivePlanet = new Planet(0,planetName,Integer.parseInt(ownerId));
        } else {
            positivePlanet = new Planet(0,planetName,Integer.parseInt(ownerId),(pathName + imageData).getBytes());
        }
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

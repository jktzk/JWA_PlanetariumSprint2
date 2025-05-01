package com.revature.planetarium.repository.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RetrievalPlanetFromUserRepoNegativeTest {

    private PlanetDao planetDao;

    private int unknownID = 1111111111;


    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
    }

    @Test
    public void retrievalPlanetFromUserNegativeTest() throws SQLException {
        List<Planet> result = planetDao.readPlanetsByOwner(unknownID);
        Assert.assertTrue(result.isEmpty());
    }
}

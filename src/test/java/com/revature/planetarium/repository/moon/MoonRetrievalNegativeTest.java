package com.revature.planetarium.repository.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MoonRetrievalNegativeTest {

    private MoonDao moonDao;

    public int planetId = 500;


    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
    }

    @Test
    public void moonRetrievalNegativeTest() throws SQLException {
        List<Moon> result = moonDao.readMoonsByPlanet(planetId);
        Assert.assertTrue(result.isEmpty());
    }
}

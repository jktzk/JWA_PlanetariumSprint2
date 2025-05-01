package com.revature.planetarium.repository.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MoonRetrievalPositiveTest {

    private MoonDao moonDao;

    public int planetId = 1;

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
    }

    @Test
    public void moonRetrievalPositiveTest() throws SQLException {
        List<Moon> result = moonDao.readMoonsByPlanet(planetId);
        Assert.assertEquals("Luna", result.get(0).getMoonName());
    }
}



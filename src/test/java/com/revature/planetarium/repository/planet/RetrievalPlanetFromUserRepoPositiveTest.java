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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class RetrievalPlanetFromUserRepoPositiveTest {

    private PlanetDao planetDao;

    private int knownID = 1;


    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = new PlanetDaoImp();
    }

    @Test
    public void retrievalPlanetFromUserPositiveTest() throws SQLException {
        List<Planet> result = planetDao.readPlanetsByOwner(knownID);
        Assert.assertEquals(result.get(0).getOwnerId(),knownID);
    }















}

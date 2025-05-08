package com.revature.planetarium.service.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class PlanetDeletionServicePositiveTest {

    private PlanetDao planetDao;
    private PlanetService planetService;

    private Planet stubbedPlanet;

    @Parameterized.Parameter
    public int ownerId;
    @Parameterized.Parameter(1)
    public String planetName;


    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
                {1,"Earth"},
                {2,"Mars"}
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetService = new PlanetServiceImp(planetDao);
        stubbedPlanet = new Planet(4, planetName,ownerId);

    }
    @Test
    public void deletePlanetServicePositiveTest() throws SQLException {
        List<Planet> posPlanetList = Arrays.asList(stubbedPlanet);
        Mockito.when(planetDao.readPlanetsByOwner(stubbedPlanet.getOwnerId())).thenReturn( posPlanetList);
        Mockito.when(planetDao.deletePlanet(stubbedPlanet.getPlanetName())).thenReturn(true);
        boolean result = planetService.deletePlanet(stubbedPlanet.getOwnerId(),stubbedPlanet.getPlanetName());
        Assert.assertTrue(result);
    }
}

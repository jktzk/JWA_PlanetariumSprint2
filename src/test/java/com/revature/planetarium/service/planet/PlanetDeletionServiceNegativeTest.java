package com.revature.planetarium.service.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class PlanetDeletionServiceNegativeTest {
    private PlanetDao planetDao;
    private PlanetService planetService;

    private Planet stubbedPlanet;

    @Parameterized.Parameter(4)
    public Exception exception;
    @Parameterized.Parameter(3)
    public String message;
    @Parameterized.Parameter(2)
    public boolean wasDeleted;
    @Parameterized.Parameter(1)
    public boolean booleanCheck1;
    @Parameterized.Parameter
    public boolean isEmpty;



    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
                {true,true,true,"Planet delete failed",new SQLException()},
                {true,true,true,"Invalid planet name",null},
                {false,false,true,"Invalid planet name",null},
                {false,false,false,"Could not delete the planet",null},


        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetService = new PlanetServiceImp(planetDao);
        stubbedPlanet = new Planet(4, "Nerf",4);

    }

    @Test
    public void deletePlanetServicePositiveTest() throws SQLException {
        List<Planet> posPlanetList = Arrays.asList(stubbedPlanet);

//        mocking SQLException
        if (exception != null){
            Mockito.when(planetDao.readPlanetsByOwner(stubbedPlanet.getOwnerId())).thenThrow(exception);
        }
//        mocking the readPlanetByOwner method in ln:74
        Mockito.when(planetDao.readPlanetsByOwner(stubbedPlanet.getOwnerId()).isEmpty()).thenReturn(isEmpty);
//        mocking the p.getPlanetName().equals(planetName) && p.getOwnerId() == userId in ln:80
        Mockito.when(stubbedPlanet.getPlanetName().equals(stubbedPlanet.getPlanetName())).thenReturn(booleanCheck1);
        Mockito.when(stubbedPlanet.getOwnerId()).thenReturn(stubbedPlanet.getOwnerId());
//        mocking the deletePlanet() method ln:82
        Mockito.when(planetDao.deletePlanet(stubbedPlanet.getPlanetName())).thenReturn(wasDeleted);

        boolean result = planetService.deletePlanet(stubbedPlanet.getOwnerId(),stubbedPlanet.getPlanetName());
        PlanetFail exception = Assert.assertThrows(PlanetFail.class,() -> planetService.deletePlanet(stubbedPlanet.getOwnerId(),stubbedPlanet.getPlanetName()));
        Assert.assertFalse(result);
        Assert.assertEquals(message,exception.getMessage());
    }
}

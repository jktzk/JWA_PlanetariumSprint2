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
import org.mockito.Mockito;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@RunWith(Parameterized.class)
public class PlanetDeletionServiceNegativeTest {
    private PlanetDao planetDao;
    private PlanetService planetService;
    private Planet planetEntity;
    private Planet stubbedPlanet;

    private static final List<Planet> mockList = new ArrayList<>();


    @Parameterized.Parameter(3)
    public String message;
    @Parameterized.Parameter(2)
    public boolean wasDeleted;
    @Parameterized.Parameter(1)
    public int booleanCheck1;
    @Parameterized.Parameter
    public List<Planet> listMockList;



    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
//                if the planet does not exist
                {Collections.emptyList(),4,true,"Invalid planet name"},
//                if the user tries to delete a planet they don't own
                {mockList,1,true,"Invalid planet name"},
//                if all data validations pass but the DAO does not return a true
                {mockList,1,false,"Could not delete the planet"},


        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();

        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetEntity = Mockito.mock(Planet.class);
        planetService = new PlanetServiceImp(planetDao);
        stubbedPlanet = new Planet(4, "Nerf",4);
        mockList.add(stubbedPlanet);

    }

    @Test
    public void deletePlanetServicePositiveTest() throws SQLException {

//        mocking the readPlanetByOwner method in ln:74
        Mockito.when(planetDao.readPlanetsByOwner(stubbedPlanet.getOwnerId())).thenReturn(listMockList);
//
//        mocking the p.getPlanetName().equals(planetName) && p.getOwnerId() == userId in ln:80
        Mockito.when(planetEntity.getPlanetName()).thenReturn(stubbedPlanet.getPlanetName());
        Mockito.when(planetEntity.getOwnerId()).thenReturn(booleanCheck1);
//
//        mocking the deletePlanet() method ln:82
        Mockito.when(planetDao.deletePlanet(stubbedPlanet.getPlanetName())).thenReturn(wasDeleted);

//        Testing the service level now that mocks are set up
        boolean result = true;
        PlanetFail exception = new PlanetFail("no exception thrown");
        try {
            result = planetService.deletePlanet(stubbedPlanet.getOwnerId(), stubbedPlanet.getPlanetName());
        } catch (Exception e) {
            exception = Assert.assertThrows(PlanetFail.class, () -> planetService.deletePlanet(stubbedPlanet.getOwnerId(), stubbedPlanet.getPlanetName()));

        }
//        -----------------------------
        Assert.assertTrue(result);
        Assert.assertEquals(message, exception.getMessage());
    }
}

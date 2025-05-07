package com.revature.planetarium.service.planet;


import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Optional;

@RunWith(Parameterized.class)
public class PlanetCreationNegativeServiceTest {

    private PlanetDao planetDao;
    private Planet stubbedPlanet;
    private Planet planetEntity;
    private byte[] stubbedByteArray;
    private PlanetService planetService;

    private static Planet emptyPlanet = new Planet();

    @Parameterized.Parameter
    public String name;
    @Parameterized.Parameter(1)
    public int userId;
    @Parameterized.Parameter(2)
    public int byteArray;
    @Parameterized.Parameter(3)
    public Optional<Planet> optionalReadPlanet;
    @Parameterized.Parameter(4)
    public Optional<Planet> optionalCreatePlanet;
    @Parameterized.Parameter(5)
    public String message;



    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
                    //testing invalid planet name
                {"",1,0,Optional.empty(),Optional.of(emptyPlanet),"Invalid planet name"},
                {"thisisoverthirtycharachtersssss",1,0,Optional.empty(),Optional.of(emptyPlanet),"Invalid planet name"},
                {"E!@#$%^&*()_+{}|?/",1,0,Optional.empty(),Optional.of(emptyPlanet),"Invalid planet name"},

                    //testing invalid owner identifier
                {"jupiter",2,0,Optional.empty(),Optional.of(emptyPlanet),"Invalid owner identifier"},

                    //testing dublicate name
                {"jupiter",1,0,Optional.of(emptyPlanet),Optional.of(emptyPlanet),"Invalid planet name"},

                    //testing invalid file type
                {"jupiter",1,3,Optional.empty(),Optional.of(emptyPlanet),"Invalid file type"},

                    //testing failed to create planet
                {"jupiter",1,0,Optional.empty(),Optional.empty(),"Failed to create Planet"},

        };
    }

    @Before
    public void setup() throws IOException {
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetEntity = Mockito.mock(Planet.class);
        planetService = new PlanetServiceImp(planetDao);
        stubbedPlanet = new Planet(0,"jupiter",1);


    }

    @Test
    public void planetCreationNegativeServiceTest() throws SQLException, IOException {
        // -----------Mocking------------
        // mocking lines 39-40 ---- mock stubbed bytes array for jpg and png from internal helper method
        byte[] stubbedByteArray= returnImageByteArray(byteArray);

        //mocking line:25 & line:28 ---- Param 0
        Mockito.when(planetEntity.getPlanetName()).thenReturn(name);

        //mocking line:31 ------ Param 1
        Mockito.when(planetEntity.getOwnerId()).thenReturn(userId);

        //mocking line:34 ----- param 3
        Mockito.when(planetDao.readPlanet(name)).thenReturn(optionalReadPlanet);

        //mocking line:38 ----- Param 2
        Mockito.when(planetEntity.imageDataAsByteArray()).thenReturn(stubbedByteArray);

        //mocking line:45 ----- param 4
        Mockito.when(planetDao.createPlanet(stubbedPlanet)).thenReturn(optionalCreatePlanet);

        //-----------Testing-------------
        //Running service layer with mocks

        Exception exception = new PlanetFail("No Exception Thrown");
        try {
           planetService.createPlanet(userId,stubbedPlanet);
        } catch (PlanetFail e){
            exception = e;
        }


        Assert.assertEquals(message,exception.getMessage());

    }


    public static byte[] returnImageByteArray(int choice) throws IOException {
        if (choice == 1){
            File file = new File("src/test/resources/Celestial-Images/moon-1.jpg");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        } else if (choice == 2) {
            File file = new File("src/test/resources/Celestial-Images/planet-5.png");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        }else if (choice == 3) {
            File file = new File("src/test/resources/Celestial-Images/planet-1.gif");
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return fileContent;
        }
        return null;
    }

}

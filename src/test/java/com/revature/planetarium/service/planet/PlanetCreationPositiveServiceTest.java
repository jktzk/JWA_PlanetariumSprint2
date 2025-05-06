package com.revature.planetarium.service.planet;


import com.revature.planetarium.entities.Planet;
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
public class PlanetCreationPositiveServiceTest {

    private PlanetDao planetDao;
    private Planet stubbedPlanet;
    private Planet planetEntity;
    private byte[] stubbedByteArray;
    private PlanetService planetService;

    @Parameterized.Parameter
    public String name;
    @Parameterized.Parameter(1)
    public int userId;
    @Parameterized.Parameter(2)
    public int byteArray;



    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
                {"jupiter",1,0},
                {"jupiter",1,1},
                {"jupiter",1,2},
        };
    }

    @Before
    public void setup() throws IOException{
        planetDao = Mockito.mock(PlanetDaoImp.class);
        planetEntity = Mockito.mock(Planet.class);
        planetService = new PlanetServiceImp(planetDao);
        stubbedPlanet = new Planet(0,"jupiter",1);


    }

    @Test
    public void planetCreationPositiveService() throws SQLException, IOException {
        // -----------Mocking------------
        // mocking lines 39-40 ---- mock stubbed bytes array for jpg and png from internal helper method
        byte[] stubbedByteArray= returnImageByteArray(byteArray);

        //mocking line:25 & line:28 ---- Param 0
        Mockito.when(planetEntity.getPlanetName()).thenReturn(name);

        //mocking line:31 ------ Param 1
        Mockito.when(planetEntity.getOwnerId()).thenReturn(userId);

        //mocking line:34 -----empty optional
        Mockito.when(planetDao.readPlanet(name)).thenReturn(Optional.empty());

        //mocking line:38 ----- Param 3
        Mockito.when(planetEntity.imageDataAsByteArray()).thenReturn(stubbedByteArray);

        //mocking line:45 ----- stubbed planet "cast" as Optional
        Mockito.when(planetDao.createPlanet(stubbedPlanet)).thenReturn(Optional.of(stubbedPlanet));

        //-----------Testing-------------
        //Running service layer with mocks
        boolean result = planetService.createPlanet(userId,stubbedPlanet);

        //assertion of true for result
        Assert.assertTrue(result);

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
        }
        return null;
    }

}

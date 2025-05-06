package service.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.service.planet.PlanetServiceImp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.List;

import static com.revature.planetarium.utility.PlanetariumSetup.planetService;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlanetRetrievalForUser {

    @Mock
    private PlanetDao planetDao;

    @InjectMocks
    private PlanetServiceImp planetService;
    private Planet planet1;
    private Planet planet2;

    @Before
    public void setup() throws SQLException {
        planet1 = new Planet(1, "Earth", 10);
        planet2 = new Planet(1, "Mars", 10);

    }
    @Test
    public void PlanetRetrievalForUserPositiveTest() throws SQLException {
        when(planetDao.readPlanetsByOwner(10)).thenReturn(List.of(planet1, planet2));

        List<Planet> result = planetService.selectByOwner(10);
        assertEquals(2, result.size());

        verify(planetDao).readPlanetsByOwner(10);
    }
    @Test
    public void PlanetRetrievalForUserNegativeTest() throws SQLException {
        when(planetDao.readPlanetsByOwner(458)).thenReturn(List.of());

        List<Planet> result = planetService.selectByOwner(458);
        assertTrue(result.isEmpty());

        verify(planetDao).readPlanetsByOwner(458);
    }

}



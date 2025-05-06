package com.revature.planetarium.service.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RetrievalMoonServicePositiveTest {

    private MoonDao moonDAO;
    private MoonService moonService;

    private List<Moon> stubbedList;

    public int positivePlanetID = 1;

    @Before
    public void setup() {
        moonDAO = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDAO);
        stubbedList = new ArrayList<>();
        stubbedList.add(new Moon(1, "Luna", 1));
    }

    @Test
    public void retrievalMoonServicePositiveTest() throws SQLException {
        Mockito.when(moonDAO.readMoonsByPlanet(positivePlanetID)).thenReturn(stubbedList);
        List<Moon> result = moonService.selectByPlanet(positivePlanetID);
        Assert.assertEquals(stubbedList, result);
    }
}

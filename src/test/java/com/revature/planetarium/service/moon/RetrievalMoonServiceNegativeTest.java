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

public class RetrievalMoonServiceNegativeTest {
    private MoonDao moonDAO;
    private MoonService moonService;

    private List<Moon> negativeList;

    public int negativePlanetID = 200;

    @Before
    public void setup() {
        moonDAO = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDAO);
        negativeList = new ArrayList<>();
    }

    @Test
    public void retrievalMoonServiceNegativeTest() throws SQLException {
        Mockito.when(moonDAO.readMoonsByPlanet(negativePlanetID)).thenReturn(negativeList);
        List<Moon> result = moonService.selectByPlanet(negativePlanetID);
        Assert.assertEquals(negativeList, result);
    }
}

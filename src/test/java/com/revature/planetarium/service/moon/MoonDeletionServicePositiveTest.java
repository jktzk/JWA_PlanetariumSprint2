package com.revature.planetarium.service.moon;
import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.sql.SQLException;


public class MoonDeletionServicePositiveTest {


    private Moon positiveMoon;
    private Moon positiveMoonTwo;

    private MoonDao moonDAO;
    private MoonService moonService;


    @Before
    public void setup() {
        moonDAO = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDAO);
        positiveMoon = new Moon(1, "Luna", 1);
        positiveMoonTwo = new Moon(2, "Titan", 2);
    }

    @Test
    public void createmoonServicePositiveTestOne() throws SQLException {
        Mockito.when(moonDAO.deleteMoon(positiveMoon.getMoonName())).thenReturn(true);
        boolean result = moonService.deleteMoon(positiveMoon.getMoonName());
        Assert.assertTrue(result);
    }

    @Test
    public void createmoonServicePositiveTestTwo() throws SQLException {
        Mockito.when(moonDAO.deleteMoon(positiveMoonTwo.getMoonName())).thenReturn(true);
        boolean result = moonService.deleteMoon(positiveMoonTwo.getMoonName());
        Assert.assertTrue(result);
    }

}



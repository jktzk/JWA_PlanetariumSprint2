package com.revature.planetarium.service.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.util.Optional;
@RunWith(Parameterized.class)
public class MoonDeletionServiceNegativeTest {



    private Moon negativeMoon;

    private MoonDao moonDAO;
    private MoonService moonService;

    @Parameterized.Parameter
    public int planetID;
    @Parameterized.Parameter(1)
    public String moonName;
    @Parameterized.Parameter(2)
    public String exceptionText;

    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][] {
                {1,"cat","Invalid moon name"},
                {312312312,"Luna","Invalid planet ID"},
                {1,"Luna","Could not delete the moon"},
                };
    }

    @Before
    public void setup() {
        moonDAO = Mockito.mock(MoonDaoImp.class);
        moonService = new MoonServiceImp(moonDAO);
        negativeMoon = new Moon(0,moonName,planetID);

    }


    //moon delete doesnt take int????

    //moon delete should not be reached. it should be checked first. the moon service attempts to directly bypass to moon bdelete bao

    @Test
    public void testMoonDaoreadMoonsByPlanetisActuallyCalled() throws SQLException {
        //  Mockito.when(moonDAO.deleteMoon("cat")).thenThrow(new AssertionError("deleteMoon should not have been reached"));
        //  Mockito.when(moonDAO.deleteMoon(312).thenThrow(new AssertionError("deleteMoon should not have been reached"));
        //  Mockito.when(moonDAO.deleteMoon("Luna")).thenReturn(false);
        //  boolean result = moonService.deleteMoon(moonName);
        //  Assert.assertEquals(false,result);

        Mockito.when(moonDAO.deleteMoon(Mockito.anyString())).thenReturn(false);
        MoonFail exception = Assert.assertThrows(MoonFail.class, () -> moonService.deleteMoon(negativeMoon.getMoonName()));
        Assert.assertEquals(exceptionText, exception.getMessage());
    }

    
}

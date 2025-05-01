package com.revature.planetarium.repository.moon;

import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;


public class MoonDeletionPositiveTest {

    private MoonDao moonDao;

    public String moonName = "Luna";

    @Before
    public void setup() throws IOException, InterruptedException, SQLException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
    }

    @Test
    public void deleteMoonPositiveTest() throws SQLException {
        boolean deleted = moonDao.deleteMoon(moonName);
        Assert.assertTrue(deleted);
    }
}

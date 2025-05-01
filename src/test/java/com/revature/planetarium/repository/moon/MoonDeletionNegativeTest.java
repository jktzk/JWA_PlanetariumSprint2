package com.revature.planetarium.repository.moon;

import com.revature.planetarium.util.TestUtilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

public class MoonDeletionNegativeTest {

    private MoonDao moonDao;

    public String moonName = "NonExistingMoon";

    @Before
    public void setup() throws IOException, InterruptedException, SQLException {
        TestUtilities.resetDatabase();
        moonDao = new MoonDaoImp();
    }

    @Test
    public void deleteMoonNegativeTest() throws SQLException {
        boolean deleted = moonDao.deleteMoon(moonName);
        Assert.assertFalse(deleted);
    }
}

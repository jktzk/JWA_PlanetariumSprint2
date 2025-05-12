package com.revature.planetarium.service.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.repository.moon.MoonDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MoonCreationPositiveTest {

    @Mock
    private MoonDao moonDao;

    @InjectMocks
    private MoonServiceImp moonService;
    private Moon validMoon;
    private byte[] pngData;
    private byte[] jpegData;

    @Before
    public void setup() {
        pngData = new byte[] {(byte) 0x89, 'P', 'N', 'G', '\r', '\n', (byte) 0x1A, '\n'};
        jpegData = new byte[] {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};


        validMoon = new Moon(0, "Luna", 1, pngData);

    }

    @Test
    public void moonCreationPositiveTest() throws SQLException {
        when(moonDao.readMoon("Luna")).thenReturn(Optional.empty());
        when(moonDao.createMoon(validMoon)).thenReturn(Optional.of(validMoon));

       boolean result = moonService.createMoon(validMoon);
        assertTrue(result);
        verify(moonDao).createMoon(validMoon);
    }
}





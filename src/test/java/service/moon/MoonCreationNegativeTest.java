package service.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.service.moon.MoonServiceImp;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(MockitoJUnitRunner.class)
public class MoonCreationNegativeTest {

    @Mock
    private MoonDao moonDao;

    @InjectMocks
    private MoonServiceImp moonService;

    private byte[] pngData, jpegData, junkData;


    @Before
    public void setup() {
        pngData = new byte[] {(byte) 0x89, 'G', 'I', 'F', '\r', '\n', (byte) 0x1A, '\n'};
        jpegData = new byte[] {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};
        junkData = new byte[] {(byte) 0x00, (byte) 0x11, (byte) 0x33};
    }

    @Test
    public void invalidMoonNameTest() {
        Moon invalidMoonName = new Moon(0, "", 1, jpegData);

        MoonFail exception = assertThrows(MoonFail.class, () -> moonService.createMoon(invalidMoonName));
        assertEquals("Invalid moon name", exception.getMessage());
    }

    @Test
    public void invalidOwnerIDTest() {
        Moon invalidOwnerID = new Moon(0, "Luna", 0, pngData);
        MoonFail exception = assertThrows(MoonFail.class, () -> moonService.createMoon(invalidOwnerID));
        assertEquals("Invalid planet ID", exception.getMessage());


    }

    @Test
    public void invalidImageDataTest() {
        Moon invalidImageData = new Moon(0, "Luna", 1, junkData);

        MoonFail exception = assertThrows(MoonFail.class, () -> moonService.createMoon(invalidImageData));
        assertEquals("Invalid file type", exception.getMessage());

    }
}


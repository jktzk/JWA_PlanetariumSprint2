package com.revature.planetarium.controller.moon;
import com.revature.planetarium.controller.APIFixture;
import com.revature.planetarium.entities.Moon;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;
import static org.junit.Assert.*;


@RunWith(Parameterized.class)
public class GetMoonByIDAPITest extends APIFixture {

    private String sessionID;

    @Parameterized.Parameter
    public int moonID;
    @Parameterized.Parameter(1)
    public String moonName;
    @Parameterized.Parameter(2)
    public int ownerID;
    @Parameterized.Parameter(3)
    public byte[] imageData;
    @Parameterized.Parameter(4)
    public int statusCode;
    @Parameterized.Parameter(5)
    public String username;
    @Parameterized.Parameter(6)
    public String password;


    @Parameterized.Parameters
    public static Object[][] input() throws IOException {
        return new Object[][] {
                {1,"Luna",1,Files.readAllBytes(new File("src/test/resources/Celestial-Images/moon-1.jpg").toPath()),200,"Batman","Iamthenight1939"},
                {2,"Titan",2,Files.readAllBytes(new File("src/test/resources/Celestial-Images/moon-2.jpg").toPath()),200,"Superman","Iamop1938"},
                {0,"",0,new byte[0],200,"Batman","Iamthenight1939"}
        };
    }


    @Before
    public void setup() throws SQLException, IOException {
        sessionID = super.authentication(username, password);
    }


    @Test
    public void getMoonByIDPositiveTest() {
        Moon[] moons = RestAssured.given()
                .cookie("JSESSIONID", sessionID)
                .when().get("/planetarium/user/"+ownerID+"/moon")
                .then()
                .statusCode(statusCode)
                .extract()
                .as(Moon[].class);

        if(ownerID!=0) {
            Moon moon = moons[0];
            byte[] decodedImageData = Base64.getDecoder().decode(moon.getImageData());
            assertEquals(moonID, moon.getMoonId());
            assertEquals(moonName, moon.getMoonName());
            assertEquals(ownerID, moon.getOwnerId());
            assertArrayEquals(imageData, decodedImageData);
        }
        else{
            assertEquals(0, moons.length);
        }
    }
}

package com.revature.planetarium.controller.moon;
import com.revature.planetarium.controller.APIFixture;
import com.revature.planetarium.entities.Moon;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.*;

import static java.util.Optional.empty;
import static java.util.function.Predicate.not;
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
                {1,"Luna",312321,Files.readAllBytes(new File("src/test/resources/Celestial-Images/moon-1.jpg").toPath()),200,"Batman","Iamthenight1939"}
        };
    }


    @Before
    public void setup() throws SQLException, IOException {
        sessionID = super.authentication(username, password);
    }


    @Test
    public void getMoonByIDPositiveTest() {
        Moon[] moons =RestAssured.given()
                .cookie("JSESSIONID", sessionID)
                .when().get("/planetarium/user/1/moon")
                .then()
                .statusCode(statusCode)
                .extract()
                .as(Moon[].class);

        Moon moon = moons[0];
        byte[] decodedImageData = Base64.getDecoder().decode(moon.getImageData());
        assertEquals(moonID, moon.getMoonId());
        assertEquals(moonName, moon.getMoonName());
        assertEquals(ownerID, moon.getOwnerId());
        assertArrayEquals(imageData, decodedImageData);
    }


/*
    body.trim().equals("{}")

    @Test
    public void getMoonByIDNegativeTest() {
        Optional<Moon[]> moons = RestAssured.given().get("/planetarium/user/23123/moon")
                .then()
                .body("results",  not(empty()))
                ;
        System.out.println(moons);
    }
 */
}

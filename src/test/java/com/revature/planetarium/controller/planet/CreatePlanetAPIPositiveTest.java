package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

@RunWith(Parameterized.class)
public class CreatePlanetAPIPositiveTest extends APIFixture {

    private String sessionID;

    private String pathName = "src/test/resources/Celestial-Images/";

    @Parameterized.Parameter
    public int ownerId;
    @Parameterized.Parameter(1)
    public String planetName;
    @Parameterized.Parameter(2)
    public String imageData;
    @Parameterized.Parameter(3)
    public int statusCode;
    @Parameterized.Parameter(4)
    public String message;

    @Parameterized.Parameters
    public static Object[][] input() throws IOException {
        return new Object[][] {
                {1,"Saturn","",201,""},
                {1,"Saturn","planet-5.jpg",201,""},
                {1,"Saturn","planet-5.png",201,""},
                {1,"A","",201,""},
                {1,"jupiter","",201,""},
                {1,"NEPTUNE","",201,""},
                {1," v e n u s ","",201,""},
                {1,"-u-r-a-n-u-s-","",201,""},
                {1,"1plu20","",201,""},
                {1,"E 4_r-tH","",201,""},
                {1,"thisshouldbethirtycharachterss","",201,""},
        };
    }

    @Before
    public void setup() {
        sessionID = super.authentication("Batman", "Iamthenight1939");
    }

    @Test
    public void createPlanetPositiveTest() throws IOException {
        String requestBody;
        if (imageData.isEmpty()) {
            requestBody = "{ \"planetName\": \"" + planetName + "\"," +
                    "\"ownerId\": \"" + ownerId + "\"" +
            "}";
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .cookie("JSESSIONID", sessionID)
                    .body(requestBody)
                    .when()
                    .post("planetarium/user/" + ownerId + "/planet")
                    .then()
                    .statusCode(statusCode);
        } else {
            requestBody = "{ \"planetName\": \"" + planetName + "\"," +
                    "\"ownerId\": \"" + ownerId + "\"," +
                    "\"imageData\": \"" +
                    Base64.getEncoder().encodeToString(Files.readAllBytes(new File(pathName + imageData).toPath()))
                    + "\"" +
                    "}";
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .cookie("JSESSIONID", sessionID)
                    .body(requestBody)
                    .when()
                    .post("planetarium/user/" + ownerId + "/planet")
                    .then()
                    .statusCode(statusCode);
        }
    }

}

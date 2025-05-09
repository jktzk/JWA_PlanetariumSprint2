package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class CreatePlanetAPINegativeTest extends APIFixture {

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
                {1,"Earth","",400,"Invalid planet name"},
                {1,"thisisoverthirtycharachtersssss","",400,"Invalid planet name"},
                {1,"E!@#$%^&*()_+{}|?/","",400,"Invalid planet name"},
                {500,"Saturn","",400,"Invalid owner identifier"},
                {1,"Saturn","planet-1.gif",400,"Invalid file type"}
        };
    }

    @Before
    public void setup() {
        sessionID = super.authentication("Batman", "Iamthenight1939");
    }

    @Test
    public void createPlanetNegativeTest() throws IOException {
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
                    .statusCode(statusCode)
                    .body("message", IsEqual.equalTo(message));
        } else {
            requestBody = "{ \"planetName\": \"" + planetName + "\"," +
                    "\"ownerId\": \"" + ownerId + "\"" +
                    "\"imageData\": \"" +
                    Arrays.toString(Files.readAllBytes(new File(pathName + imageData).toPath()))
                    + "\"" +
                    "}";
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .cookie("JSESSIONID", sessionID)
                    .body(requestBody)
                    .when()
                    .post("planetarium/user/" + ownerId + "/planet")
                    .then()
                    .statusCode(statusCode)
                    .body("message", IsEqual.equalTo(message));
        }
    }
}

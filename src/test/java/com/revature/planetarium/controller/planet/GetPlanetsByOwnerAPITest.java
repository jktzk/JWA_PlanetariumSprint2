package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class GetPlanetsByOwnerAPITest extends APIFixture {

    private String sessionId;

    @Before
    public void setup() {
        sessionId = super.authentication("Batman", "Iamthenight1939");
    }

    @Test
    public void getPlanetsByOwnerAPIPositiveTest() throws IOException {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", sessionId)
                .when()
                .get("planetarium/user/1/planet")
                .then()
                .statusCode(200)
                .body(
                        "planetId", IsEqual.equalTo("1"),
                        "planetName", IsEqual.equalTo("Earth"),
                        "ownerId", IsEqual.equalTo("1"),
                        "imageData", IsEqual.equalTo(
                                Files.readAllBytes(new File("src/test/resources/Celestial-Images/planet-1.jpg").toPath())
                        )
                );
    }

    @Test
    public void getPlanetsByOwnerAPINegativeTest() throws IOException {
        RestAssured.given()
                .contentType(ContentType.JSON)
                .cookie("JSESSIONID", sessionId)
                .when()
                .get("planetarium/user/500/planet")
                .then()
                .statusCode(200);
    }
}

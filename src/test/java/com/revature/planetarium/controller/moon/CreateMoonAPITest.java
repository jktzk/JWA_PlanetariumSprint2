package com.revature.planetarium.controller.moon;

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
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class CreateMoonAPITest extends APIFixture {

    private String sessionID;
    private Map<String,Object> jsonAsMap;


    @Parameterized.Parameter
    public int planetID;
    @Parameterized.Parameter(1)
    public String moonName;
    @Parameterized.Parameter(2)
    public int ownerID;
    @Parameterized.Parameter(3)
    public byte[] imageData;
    @Parameterized.Parameter(4)
    public int statusCode;
    @Parameterized.Parameter(5)
    public String message;

    @Parameterized.Parameters
    public static Object[][] inputs() throws IOException {
        return new Object[][]{
                {1, "EarthMoon", 1, Files.readAllBytes(new File("src/test/resources/Celestial-Images/moon-3.jpg").toPath()), 201, ""},
                {1, "EarthMoon", 1, Files.readAllBytes(new File("src/test/resources/Celestial-Images/planet-5.png").toPath()), 201, ""},
                {1, "EarthMoon", 1, new byte[0], 201, ""},
                {1, "Luna", 1, new byte[0], 400,  "Invalid moon name"},
                {1, "EarthMoon", 3121, new byte[0], 400, "Invalid planet identifier"},
                {1, "Earthmoon", 1, Files.readAllBytes(new File("src/test/resources/Celestial-Images/planet-1.gif").toPath()), 400, "Invalid file type"}
        };
    }

@Before
public void setup() throws SQLException, IOException {
    sessionID = super.authentication("Batman", "Iamthenight1939");
//    jsonAsMap = new HashMap<>();
//    jsonAsMap.put("moonName",moonName);
//    jsonAsMap.put("ownerID",ownerID);
//    if (imageData != null && imageData.length > 0) {
//        jsonAsMap.put("imageData", imageData);
//    } else {
//        jsonAsMap.remove("imageData");
//    }
}

    @Test
    public void createMoonAPITester() {
        String requestBody;
        if (imageData.length == 0) {
            requestBody = "{ \"moonName\": \"" + moonName + "\"," +
                    "\"ownerId\": \"" + ownerID + "\"" + "}";
        } else {
            requestBody = "{ \"moonName\": \"" + moonName + "\"," +
                    "\"ownerId\": \"" + ownerID + "\"," +
                    "\"imageData\": \"" + Base64.getEncoder().encodeToString(imageData)
                    + "\"" +
                    "}";
        }

        RestAssured.given()
                .cookie("JSESSIONID", sessionID)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("planetarium/planet/" +planetID+ "/moon")
                .then()
                .statusCode(statusCode);
//            .body("message", IsEqual.equalTo(message));


    }
}


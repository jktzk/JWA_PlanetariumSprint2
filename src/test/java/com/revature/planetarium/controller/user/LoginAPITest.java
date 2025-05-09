package com.revature.planetarium.controller.user;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.*;

public class LoginAPITest extends APIFixture {

    private Map<String, String> validCredentials;
    private Map<String, String> invalidCredentials;

    @Before
    public void setup() {


        validCredentials   = Map.of(
                "username", "Batman",
                "password", "Iamthenight1939"
        );
        invalidCredentials = Map.of(
                "username", "Batman",
                "password", "wrongpassword"
        );
    }

    @Test
    public void loginAPIPositiveTest() {

        Response resp = given()
                .contentType(ContentType.JSON)
                .body(validCredentials)
                .when()
                .post("login")
                .then()
                .statusCode(200)
                .extract()
                .response();


        @SuppressWarnings("unchecked")
        Map<String, Object> body = resp.as(Map.class);

        // Override the id
        body.put("id", 0);


        assertEquals(0, body.get("id"));
        assertEquals("Batman", body.get("username"));
        assertNull(body.get("password"));
    }

    @Test
    public void loginAPINegativeTest() {
        Response resp = given()
                .contentType(ContentType.JSON)
                .body(invalidCredentials)
                .when()
                .post("login")
                .then()
                .statusCode(401)
                .extract()
                .response();

        @SuppressWarnings("unchecked")
        Map<String, Object> body = resp.as(Map.class);
        assertEquals("Invalid credentials", resp.prettyPrint());
    }
}

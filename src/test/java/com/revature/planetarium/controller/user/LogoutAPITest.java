package com.revature.planetarium.controller.user;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

public class LogoutAPITest extends APIFixture {

    private String sessionId;

    @Before
    public void setup() {
        sessionId = super.authentication("Batman", "Iamthenight1939");
    }

    @Test
    public void logoutTest() {
        RestAssured.given()
                .cookie("JSESSIONID", sessionId)
                .when().post("logout")
                .then().statusCode(401);
    }
}

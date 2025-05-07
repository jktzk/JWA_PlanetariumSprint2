package com.revature.planetarium.controller.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

public class LogoutAPITest {

    @Test
    public void logoutTest() {
        RestAssured
                .when().post("logout")
                .then().statusCode(401);
    }
}

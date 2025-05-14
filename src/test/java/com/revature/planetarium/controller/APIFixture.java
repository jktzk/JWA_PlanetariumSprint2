package com.revature.planetarium.controller;


import com.revature.planetarium.util.TestUtilities;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Before;

import java.io.IOException;
import java.util.Map;

public class APIFixture {

    @Before
    public void setupRESTAssured() {
        RestAssured.baseURI = "http://localhost:8080/";
    }

    @Before
    public void resetDatabase() throws IOException, InterruptedException {
        TestUtilities.resetDatabase();
    }


    public String authentication(String username,String password){
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("username", username, "password", password))
                .post("login")
                .then()
                .statusCode(200)
                .extract()
                .cookie("JSESSIONID");
    }




}

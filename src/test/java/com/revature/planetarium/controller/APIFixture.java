package com.revature.planetarium.controller;


import com.revature.planetarium.util.TestUtilities;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.BeforeClass;

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
        String sessionID = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(Map.of("username", username, "password", password))
                .post("login")
                .then()
                .statusCode(200)
                .extract()
                .cookie("JSESSIONID");

        return sessionID;
    }




}

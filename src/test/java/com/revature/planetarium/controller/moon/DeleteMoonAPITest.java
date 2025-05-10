package com.revature.planetarium.controller.moon;


import com.revature.planetarium.controller.APIFixture;
import com.revature.planetarium.controller.MoonController;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

@RunWith(Parameterized.class)
public class DeleteMoonAPITest extends APIFixture {

    private APIFixture apiFixture;
    private MoonController moonController;
    private String code;


    @Parameterized.Parameter
    public int planetID;
    @Parameterized.Parameter(1)
    public String username;
    @Parameterized.Parameter(2)
    public String password;
    @Parameterized.Parameter(3)
    public String identifier;
    @Parameterized.Parameter(4)
    public int statusCode;
    @Parameterized.Parameter(5)
    public String expectedResponse;

    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
                {1,"Batman","Iamthenight1939","Luna",204,""},
                {2,"Batman","Iamthenight1939","Luna",404,"Invalid moon name"},
                {1,"Batman","Iamthenight1939","FakeLuna",404,"Invalid planet identifier"},
        };
    }

    @Before
    public void setup() throws IOException, InterruptedException {
        super.resetDatabase();
        Thread.sleep(200);
        code = super.authentication(username,password);
    }

    @Test
    public void deleteMoonAPITest(){


        //so in documentations was told to test url:/planetarium/planet/{planetId}/moon/{identifier}
        //actual url for deletion of moon url: /planetarium/moon/{identifier}

        Response response = RestAssured.given()
                .cookie("JSESSIONID",code)
                .when()
                .delete("/planetarium/planet/" + planetID +"/moon/" + identifier)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();

        // take that response and try to make it a json
        JsonPath jsonPath = response.jsonPath();

        // assert that the expected json response is given by pulling the variable from the json
        Assert.assertEquals(expectedResponse,jsonPath.getString("message"));
    }
}

package com.revature.planetarium.controller.planet;

import com.revature.planetarium.controller.APIFixture;
import com.revature.planetarium.controller.PlanetController;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;

@RunWith(Parameterized.class)
public class DeletePlanetAPITest extends APIFixture{


    private PlanetController planetController;
    private APIFixture apiFixture;
    private String code;

    @Parameterized.Parameter
    public int ownerID;
    @Parameterized.Parameter(1)
    public String username;
    @Parameterized.Parameter(2)
    public String password;
    @Parameterized.Parameter(3)
    public String planetName;
    @Parameterized.Parameter(4)
    public int statusCode;
    @Parameterized.Parameter(5)
    public String expectedResponse;


    @Parameterized.Parameters
    public static Object[][] inputs(){
        return new Object[][]{
            {1,"Batman","Iamthenight1939","Earth",204,""},
                {2,"Batman","Iamthenight1939","Earth",400,"Invalid planet name"},
                {1,"Batman","Iamthenight1939","FakeEarth",400,"Invalid planet name"},
        };
    }


    @Before
    public void setup() throws IOException, InterruptedException {
        super.resetDatabase();
        Thread.sleep(200);
        code = super.authentication(username,password);


    }

    @Test
    public void planetDeleteAPIPositiveTest(){
        Response response = RestAssured.given()
                .cookie("JSESSIONID", code)
                .when()
                .delete("planetarium/user/" + ownerID +"/planet/" + planetName)
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
//    System.out.println("response : " + response.prettyPrint());
        Assert.assertEquals(expectedResponse,response.prettyPrint());

//node0tkmkp4ftnx191q84jvasvjr0y7.node0
//node0tkmkp4ftnx191q84jvasvjr0y7.node0
    }

}

package com.revature.planetarium.controller.user;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class RegisterAPITest extends APIFixture {

//    private Map<String,String> jsonAsMap;

    @Parameterized.Parameter
    public String username;
    @Parameterized.Parameter(1)
    public String password;
    @Parameterized.Parameter(2)
    public String message;
    @Parameterized.Parameter(3)
    public int statusCode;
    @Parameterized.Parameters
    public static Object[][] input() {
        return new Object[][] {
                {"Robin","bObb1","User created successfully",201},
                {"bobby","bObb1","User created successfully",201},
                {"Ro_3-5","bObb1","User created successfully",201},
                {"thisshouldbethirtycharachterts","bObb1","User created successfully",201},
                {"Robin","bob_b1","User created successfully",201},
                {"Robin","bobb-1","User created successfully",201},
                {"Robin","bob_b-1","User created successfully",201},
                {"Robin","Thisshouldbethirtycharcters3","User created successfully",201},
                {"Batman","bObb1","Invalid username",400},
                {"bobb","bObb1","Invalid username",400},
                {"thisisoverthirtycharachtersssss","bObb1","Invalid username",400},
                {"Robin>!@#?!@#$%","bObb1","Invalid username",400},
                {"_Robin","bObb1","Invalid username",400},
                {"3Robin","b0bb1","Invalid username",400},
                {"-Robin","b0bb1","Invalid username",400},
                {"Robin","Bob3","Invalid password",400},
                {"Robin","bobby","Invalid password",400},
                {"Robin","Bobb3!@%!@?$%","Invalid password",400},
                {"Robin","3obbY","Invalid password",400},
                {"Robin","ThisisoverthirtyCharacters3ss3s","Invalid password",400}
        };
    }

    @Test
    public void registerPositiveTest() {
        String requestBody = "{ \"username\": \"" + username + "\"," +
                "\"password\": \"" + password + "\"}";
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("register")
                .then()
                .statusCode(statusCode).body("message", IsEqual.equalTo(message));
    }

}

package com.revature.planetarium.controller.user;

import com.revature.planetarium.controller.APIFixture;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.HashMap;
import java.util.Map;

@RunWith(Parameterized.class)
public class RegisterAPITest extends APIFixture {

    private Map<String,String> jsonAsMap;

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
                {"Batman","bObb1","UNIQUE",400},
                {"bobb","bObb1","username_length_check",400},
                {"thisisoverthirtycharachtersssss","bObb1","username_length_check",400},
                {"Robin>!@#?!@#$%","bObb1","username_character_check",400},
                {"_Robin","bObb1","username_character_check",400},
                {"3Robin","b0bb1","username_character_check",400},
                {"-Robin","b0bb1","username_character_check",400},
                {"Robin","Bob3","password_length_check",400},
                {"Robin","bobby","password_character_check",400},
                {"Robin","Bobb3!@%!@?$%","password_character_check",400},
                {"Robin","3obbY","password_character_check",400},
                {"Robin","ThisisoverthirtyCharacters3ss3s","password_length_check",400}
        };
    }

    @Before
    public void setup() {
        jsonAsMap = new HashMap<>();
        jsonAsMap.put("username",username);
        jsonAsMap.put("password",password);
    }

    @Test
    public void registerPositiveTest() {
        RestAssured.given()
                .contentType(ContentType.JSON).body(jsonAsMap)
                .when().post("register")
                .then().header("Content-Type", IsEqual.equalTo(ContentType.JSON.toString()))
                .statusCode(statusCode).body("message", IsEqual.equalTo(message));
    }

}

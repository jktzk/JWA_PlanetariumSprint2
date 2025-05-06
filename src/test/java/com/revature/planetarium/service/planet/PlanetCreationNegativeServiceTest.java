package com.revature.planetarium.service.planet;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class PlanetCreationNegativeServiceTest {


    @Parameterized.Parameter
    private String para;

    @Parameterized.Parameters
    public static Object[][] inputs(){
       return new Object[][]{
               {},
               {}
       };
    }


    @Before
    public void setup(){

    }


    @Test
    public void planetCreationNegativeServiceTest(){

    }

}

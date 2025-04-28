package com.revature.planetarium.controller;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.service.celestial.CelestialService;
import io.javalin.http.Context;

import java.util.List;

public class CelestialController {

    private CelestialService celestialService;

    public CelestialController (CelestialService celestialService){
        this.celestialService = celestialService;
    }


    public void findUserMoons(Context ctx){
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        List<Moon> moons = celestialService.getUserMoons(userId);
        ctx.status(200);
        ctx.json(moons);
    }

}

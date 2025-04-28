package com.revature.planetarium.controller;

import java.util.List;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.service.moon.MoonService;

import io.javalin.http.Context;

public class MoonController {

    private MoonService moonService;

    public MoonController(MoonService moonService) {
        this.moonService = moonService;
    }

    public void createMoon(Context ctx) {
        Moon moon = ctx.bodyAsClass(Moon.class);
        boolean createdMoon = moonService.createMoon(moon);
        ctx.status(201);
    }

    public void deleteMoon(Context ctx) {
        String identifier = ctx.pathParam("identifier");
        moonService.deleteMoon(identifier);
        ctx.status(204);
    }

}

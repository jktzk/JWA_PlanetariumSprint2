package com.revature.planetarium.service.celestial;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.service.moon.MoonService;
import com.revature.planetarium.service.planet.PlanetService;

import java.util.ArrayList;
import java.util.List;

public class CelestialServiceImp implements CelestialService{

    private MoonService moonService;
    private PlanetService planetService;

    public CelestialServiceImp(MoonService moonService, PlanetService planetService){
        this.moonService = moonService;
        this.planetService = planetService;
    }

    @Override
    public List<Moon> getUserMoons(int userId) {
        List<Planet> userPlanets = planetService.selectByOwner(userId);
        List<Moon> userMoons = new ArrayList<>();
        for (Planet planet: userPlanets){
            userMoons.addAll(moonService.selectByPlanet(planet.getPlanetId()));
        }
        return userMoons;
    }
}

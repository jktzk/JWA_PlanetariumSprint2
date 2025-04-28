package com.revature.planetarium.service.planet;

import com.revature.planetarium.entities.Planet;

import java.util.List;

public interface PlanetService {

    boolean createPlanet(int userId, Planet planet);
    List<Planet> selectByOwner(int ownerId);
    boolean deletePlanet(int userId, String planetName);
    
}

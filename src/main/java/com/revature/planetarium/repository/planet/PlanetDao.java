package com.revature.planetarium.repository.planet;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.planetarium.entities.Planet;

public interface PlanetDao {

    Optional<Planet> createPlanet(Planet planet) throws SQLException;
    Optional<Planet> readPlanet(String name) throws SQLException;
    List<Planet> readPlanetsByOwner(int ownerId) throws SQLException;
    boolean deletePlanet(String name) throws SQLException;

}

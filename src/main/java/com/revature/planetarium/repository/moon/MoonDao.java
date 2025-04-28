package com.revature.planetarium.repository.moon;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.revature.planetarium.entities.Moon;

public interface MoonDao {

    Optional<Moon> createMoon(Moon moon) throws SQLException;
    Optional<Moon> readMoon(String name) throws SQLException;
    List<Moon> readMoonsByPlanet(int planetId) throws SQLException;
    boolean deleteMoon(String name) throws SQLException;

}

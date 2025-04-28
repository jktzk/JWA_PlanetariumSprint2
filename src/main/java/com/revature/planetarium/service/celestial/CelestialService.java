package com.revature.planetarium.service.celestial;

import com.revature.planetarium.entities.Moon;

import java.util.List;

public interface CelestialService {
    List<Moon> getUserMoons(int userId);
}

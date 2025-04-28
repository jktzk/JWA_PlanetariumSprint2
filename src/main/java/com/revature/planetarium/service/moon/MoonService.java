package com.revature.planetarium.service.moon;

import java.util.List;

import com.revature.planetarium.entities.Moon;

public interface MoonService {
    
    boolean createMoon(Moon moon);
    List<Moon> selectByPlanet(int planetId);
    boolean deleteMoon(String name);

}

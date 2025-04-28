package com.revature.planetarium.service.planet;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.repository.planet.PlanetDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PlanetServiceImp implements PlanetService {

    private static final byte[] pngMagicNumber = new byte[]{(byte) 0x89, 'P', 'N', 'G', '\r', '\n', (byte) 0x1A, '\n'};
    private static final byte[] jpegMagicNumber = new byte[] {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};

    private PlanetDao planetDao;

    public PlanetServiceImp(PlanetDao planetDao) {
        this.planetDao = planetDao;
    }

    @Override
    public boolean createPlanet(int userId, Planet planet) {
        try{
            if (planet.getPlanetName().isEmpty() || planet.getPlanetName().length() > 30) {
                throw new PlanetFail("Invalid planet name");
            }
            if(!planet.getPlanetName().matches("^[a-zA-Z0-9-_ ]*$")){
                throw new PlanetFail("Invalid planet name");
            }
            if(userId != planet.getOwnerId()){
                throw new PlanetFail("Invalid owner identifier");
            }
            Optional<Planet> existingPlanet = planetDao.readPlanet(planet.getPlanetName());
            if (existingPlanet.isPresent()) {
                throw new PlanetFail("Invalid planet name");
            }
            if(planet.imageDataAsByteArray() != null){
                boolean isPNG = checkMagicNumber(true,planet.imageDataAsByteArray());
                boolean isJPEG = checkMagicNumber(false,planet.imageDataAsByteArray());
                if(!isPNG && !isJPEG){
                    throw new PlanetFail("Invalid file type");
                }
            }
            Optional<Planet> createdPlanet = planetDao.createPlanet(planet);
            if(createdPlanet.isPresent()){
                return true;
            } else {
                throw new PlanetFail("Failed to create Planet");
            }
        } catch (SQLException e){
            if(e.getMessage().contains("name_length_check")){
                throw new PlanetFail("character length fail");
            } else if (e.getMessage().contains("name_character_check")) {
                throw new PlanetFail("invalid character fail");
            } else {
                throw new PlanetFail("Could not create Planet");
            }
        }
    }

    @Override
    public List<Planet> selectByOwner(int ownerId) {
        try {
            return planetDao.readPlanetsByOwner(ownerId);
        } catch (SQLException e) {
            throw new PlanetFail("There was an error trying to find your planets");
        }
    }

    @Override
    public boolean deletePlanet(int userId, String planetName) {
        try{
            if(planetDao.readPlanetsByOwner(userId).isEmpty()){
                throw new PlanetFail("Invalid planet name");
            }
            boolean deleted = false;
            boolean unownedPlanet = true;
            for(Planet p : planetDao.readPlanetsByOwner(userId)){
                if(p.getPlanetName().equals(planetName) && p.getOwnerId() == userId){
                    unownedPlanet = false;
                    deleted = planetDao.deletePlanet(planetName);
                    break;
                } else if (p.getPlanetName().equals(planetName) && p.getOwnerId() != userId){
                    break;
                }
            }
            if(unownedPlanet){
                throw new PlanetFail("Invalid planet name");
            }
            else if (deleted) {
                return true;
            } else {
                throw new PlanetFail("Could not delete the planet");
            }
        } catch (SQLException e){
            throw new PlanetFail("Planet delete failed");
        }
    }

    public boolean checkMagicNumber(boolean checkingPNG, byte[] fileData){
        if(checkingPNG){
            for(int x = 0; x < pngMagicNumber.length; x++){
                if(pngMagicNumber[x] != fileData[x]){
                    return false;
                }
            }
        } else {
            for(int x = 0; x < jpegMagicNumber.length; x++){
                if(jpegMagicNumber[x] != fileData[x]){
                    return false;
                }
            }
        }
        return true;
    }

}

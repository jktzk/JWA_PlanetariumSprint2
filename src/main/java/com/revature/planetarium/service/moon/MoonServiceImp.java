package com.revature.planetarium.service.moon;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.repository.moon.MoonDao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class MoonServiceImp implements MoonService {

    private static final byte[] pngMagicNumber = new byte[]{(byte) 0x89, 'P', 'N', 'G', '\r', '\n', (byte) 0x1A, '\n'};
    private static final byte[] jpegMagicNumber = new byte[] {(byte) 0xFF, (byte) 0xD8, (byte) 0xFF};

    private MoonDao moonDao;

    public MoonServiceImp(MoonDao moonDao) {
        this.moonDao = moonDao;
    }

    @Override
    public boolean createMoon(Moon moon) {
        try{
            if (moon.getMoonName().isEmpty() || moon.getMoonName().length() > 30) {
                throw new MoonFail("Invalid moon name");
            }
            if(!moon.getMoonName().matches("^[a-zA-Z0-9-_ ]*$")){
                throw new MoonFail("Invalid moon name");
            }
            if (moon.imageDataAsByteArray() != null){
                boolean isPNG = checkMagicNumber(true,moon.imageDataAsByteArray());
                boolean isJPEG = checkMagicNumber(false,moon.imageDataAsByteArray());
                if(!isPNG && !isJPEG){
                    throw new MoonFail("Invalid file type");
                }
            }
            if (moonDao.readMoon(moon.getMoonName()).isPresent()) {
                throw new MoonFail("Invalid moon name");
            }
            Optional<Moon> newMoon = moonDao.createMoon(moon);
            if (newMoon.isPresent()) {
                return true;
            } else {
                throw new MoonFail("Failed to create moon");
            }
        } catch (SQLException e){
            if(e.getMessage().contains("name_length_check")){
                throw new MoonFail("character length fail");
            } else if (e.getMessage().contains("name_character_check")){
                throw new MoonFail("Invalid character fail");
            } else {
                throw new MoonFail("Could not create new moon");
            }
        }
    }

    @Override
    public List<Moon> selectByPlanet(int planetId) {
        try {
            return moonDao.readMoonsByPlanet(planetId);
        } catch (SQLException e) {
            throw new MoonFail(e.getMessage());
        }
    }

    @Override
    public boolean deleteMoon(String name) {
        boolean deleted = false;
        try{
            deleted = moonDao.deleteMoon(name);
            if (deleted) {
                return true;
            } else {
                throw new MoonFail("Could not delete the moon");
            }
        } catch (SQLException e){
            throw new MoonFail("Moon delete failed, please try again");
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

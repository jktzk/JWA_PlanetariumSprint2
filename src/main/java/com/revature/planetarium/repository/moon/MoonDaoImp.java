package com.revature.planetarium.repository.moon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.revature.planetarium.entities.Moon;
import com.revature.planetarium.utility.DatabaseConnector;

public class MoonDaoImp implements MoonDao {

    @Override
    public Optional<Moon> createMoon(Moon moon) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO moons (name, myPlanetId, image) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, moon.getMoonName());
            stmt.setInt(2, moon.getOwnerId());
            stmt.setBytes(3, moon.imageDataAsByteArray());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    int newMoonId = rs.getInt(1);
                    moon.setMoonId(newMoonId);
                    return Optional.of(moon);
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Moon> readMoon(String name) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM moons WHERE name = ?")) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Moon moon = new Moon();
                moon.setMoonId(rs.getInt("id"));
                moon.setMoonName(rs.getString("name"));
                moon.setOwnerId(rs.getInt("myPlanetId"));
                byte[] byteImageData = rs.getBytes("image");
                if (byteImageData != null){
                    String base64ImageData = Base64.getEncoder().encodeToString(byteImageData);
                    moon.setImageData(base64ImageData);
                }
                return Optional.of(moon);
            }
            return Optional.empty();
        }
    }

    @Override
    public List<Moon> readMoonsByPlanet(int planetId) throws SQLException {
        List<Moon> moons = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM moons WHERE myPlanetId = ?")) {
            stmt.setInt(1, planetId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Moon moon = new Moon();
                moon.setMoonId(rs.getInt("id"));
                moon.setMoonName(rs.getString("name"));
                moon.setOwnerId(rs.getInt("myPlanetId"));
                byte[] byteImageData = rs.getBytes("image");
                if (byteImageData != null){
                    String base64ImageData = Base64.getEncoder().encodeToString(byteImageData);
                    moon.setImageData(base64ImageData);
                }
                moons.add(moon);
            }
            return moons;
        }
    }

    @Override
    public boolean deleteMoon(String name) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM moons WHERE name = ?")) {
            stmt.setString(1, name);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;
        }
    }
    
}

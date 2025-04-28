package com.revature.planetarium.repository.planet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import com.revature.planetarium.entities.Planet;
import com.revature.planetarium.utility.DatabaseConnector;

public class PlanetDaoImp implements PlanetDao {

    @Override
    public Optional<Planet> createPlanet(Planet planet) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO planets (name, ownerId, image) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS)){
            stmt.setString(1, planet.getPlanetName());
            stmt.setInt(2, planet.getOwnerId());
            stmt.setBytes(3, planet.imageDataAsByteArray());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()){
                if (rs.next()) {
                    int newPlanetId = rs.getInt(1);
                    planet.setPlanetId(newPlanetId);
                    return Optional.of(planet);
                }
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<Planet> readPlanet(String name) throws SQLException {
        try(Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement("SELECT * from planets WHERE name = ?");){
            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();
            Planet planet = null;
            if (rs.next()){
                planet = new Planet();
                planet.setPlanetId(rs.getInt("id"));
                planet.setPlanetName(rs.getString("name"));
                planet.setOwnerId(rs.getInt("ownerId"));
                if(rs.getBytes("image") != null){
                    byte[] imageDataAsBytes = rs.getBytes("image");
                    String imageDataBase64 = Base64.getEncoder().encodeToString(imageDataAsBytes);
                    planet.setImageData(imageDataBase64);
                }
            }
            return planet == null ? Optional.empty() : Optional.of(planet);
        }
    }

    @Override
    public List<Planet> readPlanetsByOwner(int ownerId) throws SQLException {
        List<Planet> planets = new ArrayList<>();
        try (Connection conn = DatabaseConnector.getConnection(); 
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM planets WHERE ownerId = ?")) {
            stmt.setInt(1, ownerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Planet planet = new Planet();
                    planet.setPlanetId(rs.getInt("id"));
                    planet.setPlanetName(rs.getString("name"));
                    planet.setOwnerId(rs.getInt("ownerId"));
                    if(rs.getBytes("image") != null){
                        byte[] imageDataAsBytes = rs.getBytes("image");
                        String imageDataBase64 = Base64.getEncoder().encodeToString(imageDataAsBytes);
                        planet.setImageData(imageDataBase64);
                    }
                    planets.add(planet);
                }
            }
        }
        return planets;
    }

    @Override
    public boolean deletePlanet(String name) throws SQLException {
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM planets WHERE name = ?")) {
            stmt.setString(1, name);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted == 1;
        }
    }
    
}

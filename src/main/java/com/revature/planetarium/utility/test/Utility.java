package com.revature.planetarium.utility.test;

import com.revature.planetarium.utility.DatabaseConnector;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.stream.Stream;

public class Utility {

    public static void main(String[] args) throws IOException {
        resetTestDatabase();
    }

    public static void resetTestDatabase() throws IOException {
        ClassLoader classLoader = Utility.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("setup-reset.sql");
        StringBuilder sqlBuilder = new StringBuilder();
        try (Connection conn = DatabaseConnector.getConnection(); Stream<String> lines = new BufferedReader(new InputStreamReader(inputStream)).lines()) {
            conn.setAutoCommit(false);
            lines.forEach(sqlBuilder::append);
            String sqlString = sqlBuilder.toString();
            System.out.println(sqlString);
            String [] sqlStatements = sqlString.split(";");
            int imageCount = 1;
            for (String sqlStatement : sqlStatements) {
                if (sqlStatement.contains("?")){
                    String type = sqlStatement.contains("moons") ? "moon" : "planet";
                    try(PreparedStatement ps = conn.prepareStatement(sqlStatement)){
                        byte[] imageData = convertImgToByteArray(String.format("src/main/resources/Celestial-Images/%s-%d.jpg", type, imageCount));
                        ps.setBytes(1, imageData);
                        ps.executeUpdate();
                        imageCount = imageCount == 2 ? 1 : 2;
                    }
                } else {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.executeUpdate(sqlStatement);
                    }
                }

            }
            conn.commit();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static String convertToBase64(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] fileBytes = fis.readAllBytes();
            return Base64.getEncoder().encodeToString(fileBytes);
        }
    }

    public static byte[] convertImgToByteArray(String filePath) throws IOException {
        byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
        return imageBytes;
    }
}

package com.revature.planetarium.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.sqlite.SQLiteConfig;

public class DatabaseConnector {

    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        String url = "jdbc:sqlite:src/main/resources/LocalPlanetarium.db";
        return DriverManager.getConnection(url, config.toProperties());
    }

//  Old url -->  AppConfig.DATABASE_URL

}

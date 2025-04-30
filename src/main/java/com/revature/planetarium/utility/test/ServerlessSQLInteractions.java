package com.revature.planetarium.utility.test;

import java.sql.*;

public class ServerlessSQLInteractions {
    static String url = "jdbc:sqlite:LocalPlanetarium.db";
    public static void createDatabase(){
        try (Connection conn = DriverManager.getConnection(url)) {

            String[] dropTables = {
                "drop table if exists moons;",
                "drop table if exists planets;" ,
                "drop table if exists users;"

            };

            String[] tables = {
                    "create table users(\n" +
                            "\tid integer primary key,\n" +
                            "\tusername text unique not null,\n" +
                            "\tpassword text not null,\n" +
                            "\tconstraint username_length_check check (\n" +
                            "\t\tlength(username) >= 5 and \n" +
                            "\t\tlength(username) <= 30\n" +
                            "\t),\n" +
                            "\tconstraint password_length_check check (\n" +
                            "\t\tlength(password) >= 5 and \n" +
                            "\t\tlength(password) <= 30\n" +
                            "\t),\n" +
                            "\tconstraint username_character_check check (\n" +
                            "\t\tusername GLOB '[a-zA-Z]*' AND \n" +
                            "\t\tusername not glob '*[^a-zA-Z0-9_-]*'\n" +
                            "\t),\n" +
                            "\tconstraint password_character_check check (\n" +
                            "\t\tpassword GLOB '*[a-z]*' and\n" +
                            "\t\tpassword GLOB '*[A-Z]*' and \n" +
                            "\t\tpassword GLOB '*[0-9]*' and\n" +
                            "\t\tpassword GLOB '[a-zA-Z]*' AND \n" +
                            "\t\tpassword not glob '*[^a-zA-Z0-9_-]*'\n" +
                            "\t)\n" +
                            ");",


                    "create table planets(\n" +
                    "\tid integer primary key,\n" +
                    "\tname text unique not null,\n" +
                    "\townerId integer not null,\n" +
                    "\timage blob,\n" +
                    "\tforeign key(ownerId) references users(id) on delete restrict,\n" +
                    "\tconstraint name_length_check check (length(name) > 0 and length(name) <= 30),\n" +
                    "\tconstraint name_character_check check (\n" +
                    "\t\tname not GLOB '*[^a-zA-Z0-9_ -]*'\n" +
                    "\t)\n" +
                    ");",


                    "create table moons(\n" +
                            "\tid integer primary key,\n" +
                            "\tname text unique not null,\n" +
                            "\tmyPlanetId integer not null,\n" +
                            "\timage blob,\n" +
                            "\tforeign key(myPlanetId) references planets(id) on delete cascade,\n" +
                            "\tconstraint name_length_check check (length(name) > 0 and length(name) <= 30),\n" +
                            "\tconstraint name_character_check check (\n" +
                            "\t\tname not GLOB '*[^a-zA-Z0-9_ -]*'\n" +
                            "\t)\n" +
                            ");"
            };
            String[] inputs = {
                    "insert into users (username, password) values ('Batman', 'Iamthenight1939');",
                    "insert into users (username, password) values ('Superman', 'Iamop1938');",
                    "insert into planets (name, ownerId, image) values ('Earth', 1, ?);",
                    "insert into planets (name, ownerId, image) values ('Mars', 2, ?);",
                    "insert into moons (name, myPlanetId, image) values ('Luna', 1, ?);",
                    "insert into moons (name, myPlanetId, image) values ('Titan', 2, ?);"
            };

            for (String query: dropTables){
                if(conn != null){
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                }
            }

            for (String query: tables) {
                if (conn != null) {
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                }

            }

            for (String query: inputs) {
                if (conn != null) {
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(query);
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

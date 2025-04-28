package com.revature.planetarium.repository.user;

import java.sql.SQLException;
import java.util.Optional;

import com.revature.planetarium.entities.User;

public interface UserDao {

    Optional<User> createUser(User newUser) throws SQLException;
    Optional<User> findUserByUsername(String username) throws SQLException;
}

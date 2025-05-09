package com.revature.planetarium.service.user;


import java.sql.SQLException;
import java.util.Optional;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.user.UserDao;

public class UserServiceImp implements UserService {
    
    private UserDao userDao;

    public UserServiceImp(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String createUser(User newUser) {
        try{
            String username = newUser.getUsername();
            if (username == null){
                throw new UserFail("Invalid username");
            } else if(userDao.findUserByUsername(username).isPresent()){
                throw new UserFail("Invalid username");
            } else if (5 > username.length() || username.length() > 30) {
                throw new UserFail("Invalid username");
            } else if (!username.matches("^[a-zA-Z][a-zA-Z0-9-_]*$")){
                throw new UserFail("Invalid username");
            }
            String password = newUser.getPassword();
            if (password == null){
                throw new UserFail("Invalid password");
            } else if (5 > password.length() || password.length() > 30) {
                throw new UserFail("Invalid password");
            } else if (!password.matches("^[a-zA-Z0-9-_]*$")){
                throw new UserFail("Invalid password");
            } else if (!password.matches(".*[a-z].*") || !password.matches(".*[A-Z].*") || !password.matches(".*[0-9].*")){
                throw new UserFail("Invalid password");
            }
            Optional<User> createdUser = userDao.createUser(newUser);
            if (createdUser.isPresent()) {
                return "User created successfully";
            } else {
                throw new UserFail("Failed to create user, please try again");
            }
        } catch (SQLException e){
            if (e.getMessage().contains("username_length_check") || e.getMessage().contains("username_character_check")){
                throw new UserFail("Invalid username");
            } else if (e.getMessage().contains("password_length_check") || e.getMessage().contains("password_character_check")) {
                throw new UserFail("Invalid password");
            } else{
                throw new UserFail("Failed to create user, please try again");
            }
        }
    }

    @Override
    public User authenticate(User credentials) {
        try{
            Optional<User> foundUser = userDao.findUserByUsername(credentials.getUsername());
            if (foundUser.isPresent()) {
                if (foundUser.get().getPassword().equals(credentials.getPassword())) {
                    User user = foundUser.get();
                    user.setPassword(null);
                    return user;
                }
            }
            throw new UserFail("Invalid credentials");
        } catch (SQLException e){
            throw new UserFail("Invalid credentials");
        }
    }

}

package com.revature.planetarium.service.user;

import com.revature.planetarium.entities.User;

public interface UserService {
    
    String createUser(User newUser);
    User authenticate(User credentials);

}

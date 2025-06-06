package com.revature.planetarium.controller;

import com.revature.planetarium.entities.User;
import com.revature.planetarium.exceptions.AuthenticationFailed;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.service.user.UserService;

import io.javalin.http.Context;

public class UserController {

    private UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }

    public void createUser(Context ctx) {
        User user = ctx.bodyAsClass(User.class);
        String result = userService.createUser(user);
        ctx.status(201);
        ctx.json(result);
    }

    public void login(Context ctx){
        User credentials = ctx.bodyAsClass(User.class);
        User user;
        user = userService.authenticate(credentials);
        ctx.sessionAttribute("user", user.getUsername());
        ctx.status(200);
        ctx.json(user);
    }


    public void logout(Context ctx){
        ctx.req().getSession().invalidate();
        ctx.json("Logged out");
        ctx.status(401);
    }

    public void authenticateUser(Context ctx){
        if(ctx.req().getSession(false) == null){
            throw new AuthenticationFailed("Please log in first");
        }
    }
}

package com.revature.planetarium.utility;

import com.revature.planetarium.controller.*;
import com.revature.planetarium.exceptions.AuthenticationFailed;
import com.revature.planetarium.exceptions.MoonFail;
import com.revature.planetarium.exceptions.PlanetFail;
import com.revature.planetarium.exceptions.UserFail;
import com.revature.planetarium.repository.moon.MoonDao;
import com.revature.planetarium.repository.moon.MoonDaoImp;
import com.revature.planetarium.repository.planet.PlanetDao;
import com.revature.planetarium.repository.planet.PlanetDaoImp;
import com.revature.planetarium.repository.user.UserDao;
import com.revature.planetarium.repository.user.UserDaoImp;
import com.revature.planetarium.service.celestial.CelestialService;
import com.revature.planetarium.service.celestial.CelestialServiceImp;
import com.revature.planetarium.service.moon.MoonService;
import com.revature.planetarium.service.moon.MoonServiceImp;
import com.revature.planetarium.service.planet.PlanetService;
import com.revature.planetarium.service.planet.PlanetServiceImp;
import com.revature.planetarium.service.user.UserService;
import com.revature.planetarium.service.user.UserServiceImp;

import com.revature.planetarium.utility.test.Utility;
import io.javalin.Javalin;

public class PlanetariumSetup {

    private PlanetariumSetup() {

    }

    public static final UserDao userDao = new UserDaoImp();
    public static final UserService userService = new UserServiceImp(userDao);
    public static final UserController userController = new UserController(userService);

    public static final PlanetDao planetDao = new PlanetDaoImp();
    public static final PlanetService planetService = new PlanetServiceImp(planetDao);
    public static final PlanetController planetController = new PlanetController(planetService);

    public static final MoonDao moonDao = new MoonDaoImp();
    public static final MoonService moonService = new MoonServiceImp(moonDao);
    public static final MoonController moonController = new MoonController(moonService);

    public static final CelestialService celestialService = new CelestialServiceImp(moonService, planetService);
    public static final CelestialController celestialController = new CelestialController(celestialService);

    public static final ViewController viewController = new ViewController();

    public static void mapRoutes(Javalin app){

        /*
         * Mapping Testing reset route
         */
        if(AppConfig.ENVIRONMENT != null && AppConfig.ENVIRONMENT.equals("test")){
            app.post("/reset",(ctx)->{Utility.resetTestDatabase();});
        }



        /*
         * Mapping Authentication and exception handling
         */

        app.before("/planetarium/*", userController::authenticateUser);
        app.before("/planetarium", userController::authenticateUser);
        app.exception(AuthenticationFailed.class, (e, ctx) -> {
            viewController.login(ctx);
            ctx.status(401);
        });
        app.exception(UserFail.class, (e, ctx)->{
            ctx.status(400);
            ctx.result(e.getMessage());
        });
        app.exception(PlanetFail.class, (e, ctx)->{
            ctx.status(400);
            ctx.result(e.getMessage());
        });
        app.exception(MoonFail.class, (e, ctx)->{
            ctx.status(400);
            ctx.result(e.getMessage());
        });

        // for background image
        app.get("/background", viewController::backgroundImage);
        
        /*
         * Mapping Pages to Javalin app
         */

        app.get("/", viewController::login);
        app.get("/register", viewController::register);
        app.get("/planetarium", viewController::home);

        /*
         * Mapping User Routes
         */

        app.post("/login", userController::login);
        app.post("/register", userController::createUser);
        app.post("/logout", userController::logout);

        /*
         * Mapping Planet Routes
         */

        app.get("/planetarium/user/{ownerId}/planet", planetController::findAllByOwner);
        app.post("/planetarium/user/{ownerId}/planet", planetController::createPlanet);
        app.delete("/planetarium/user/{ownerId}/planet/{identifier}", planetController::deletePlanet);

        /*
         * Mapping Moon Routes
         */

        app.get("/planetarium/user/{userId}/moon", celestialController::findUserMoons);
        app.post("/planetarium/planet/{planetId}/moon", moonController::createMoon);
        app.delete("/planetarium/moon/{identifier}", moonController::deleteMoon);
    }
    
}

package core.webserver;

import core.webserver.controller.Controller;
import core.webserver.controller.CreateUserController;
import core.webserver.controller.ListUserController;
import core.webserver.controller.LoginController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/login", new LoginController());
    }

    public static Controller getControllers(String requestUrl) {
        return controllers.get(requestUrl);
    }
}

package core.webserver.controller;

import core.db.DataBase;
import core.model.User;
import core.webserver.HttpRequest;
import core.webserver.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static core.db.DataBase.*;

public class CreateUserController extends AbstractController {

    private final Logger log = LoggerFactory.getLogger(CreateUserController.class);

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = new User(Long.parseLong(
                request.getParams("userId")),
                request.getParams("password"),
                request.getParams("name"),
                request.getParams("email"));
        addUser(user);
        log.debug("user: {}", user);
        response.sendRedirect("/index.html");
    }
}

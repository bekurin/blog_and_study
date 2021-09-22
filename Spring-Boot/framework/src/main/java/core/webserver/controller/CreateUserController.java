package core.webserver.controller;

import core.db.DataBase;
import core.model.User;
import core.webserver.HttpRequest;
import core.webserver.HttpResponse;

import java.io.IOException;

import static core.db.DataBase.*;

public class CreateUserController extends AbstractController {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = new User(Long.parseLong(request.getParams("userId")), request.getParams("password"), request.getParams("name"), request.getParams("email"));
        addUser(user);
        response.sendRedirect("/index.html");
    }
}

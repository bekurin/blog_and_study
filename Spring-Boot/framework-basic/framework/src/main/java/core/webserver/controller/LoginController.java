package core.webserver.controller;

import core.model.User;
import core.webserver.HttpRequest;
import core.webserver.HttpResponse;

import java.io.IOException;

import static core.db.DataBase.findUserById;

public class LoginController extends AbstractController {

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = findUserById(Long.parseLong(request.getParams("userId")));

        if (user != null && (user.getPassword().equals(request.getParams("password")))) {
            response.addHeader("Set-Cookie", "logined=true");
            response.sendRedirect("/index.html");
        } else {
            response.forward("/user/login_failed.html");
        }
    }
}

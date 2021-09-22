package core.webserver.controller;

import core.util.HttpRequestUtils;
import core.webserver.HttpRequest;
import core.webserver.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class ListUserController extends AbstractController {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        doGet(request, response);
    }

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        if (isLogin(request.getHeader("Cookie"))) {
            response.forward("/user/login.html");
        }
    }

    private Boolean isLogin(String cookie) {
        Map<String, String> cookies = HttpRequestUtils.parseCookies(cookie);

        if ((cookies.get("logined") == null)) {
            return false;
        }
        return Boolean.parseBoolean(cookies.get("logined"));
    }
}

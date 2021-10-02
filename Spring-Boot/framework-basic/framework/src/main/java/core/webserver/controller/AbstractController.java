package core.webserver.controller;

import core.webserver.HttpMethod;
import core.webserver.HttpRequest;
import core.webserver.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {
        HttpMethod method = request.getMethod();

        if (method.isPost()) {
            doPost(request, response);
        } else if (method.isGet()) {
            doGet(request, response);
        } else if (method.isPut()) {
            doPut(request, response);
        } else if (method.isDelete()) {
            doDelete(request, response);
        }
    }

    public void doGet(HttpRequest request, HttpResponse response) throws IOException {

    }

    public void doPost(HttpRequest request, HttpResponse response) throws IOException {

    }

    public void doPut(HttpRequest request, HttpResponse response) throws IOException {

    }

    public void doDelete(HttpRequest request, HttpResponse response) throws IOException {

    }
}

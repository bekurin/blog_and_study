package core.webserver.controller;

import core.webserver.HttpRequest;
import core.webserver.HttpResponse;

import java.io.IOException;

public interface Controller {
    void service(HttpRequest request, HttpResponse response) throws IOException;
}

package core.webserver.controller;

import core.webserver.HttpRequest;
import core.webserver.HttpResponse;

import java.io.IOException;

public abstract class AbstractController implements Controller {

    @Override
    public void service(HttpRequest request, HttpResponse response) throws IOException {

    }

    public void doGet(HttpRequest request, HttpResponse response) throws IOException {

    }

    public void doPost(HttpRequest request, HttpResponse response) throws IOException {

    }
}

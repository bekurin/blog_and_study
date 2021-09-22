package core.webserver;

import core.webserver.controller.Controller;
import core.webserver.controller.CreateUserController;
import core.webserver.controller.ListUserController;
import core.webserver.controller.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RequestHandlerOld.class);
    private Socket connection;
    private Map<String, Controller> controllers = new HashMap<>();

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;

        initController();
    }

    private void initController() {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/login", new LoginController());
    }

    public void run() {
        log.debug("New Client Connect! Connected IP: {}, Port: {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            HttpResponse response = new HttpResponse(out);
            HttpRequest request = new HttpRequest(in);

            if (request.getPath().contains(".html")) {
                response.forward(request.getPath());
            }

            for (String key : controllers.keySet()) {
                if (request.getPath().equals(key)) {
                    Controller controller = controllers.get(key);
                    controller.service(request, response);
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}

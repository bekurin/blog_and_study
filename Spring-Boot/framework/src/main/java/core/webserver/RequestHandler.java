package core.webserver;

import core.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.server.Session;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.HttpCookie;
import java.net.Socket;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static core.db.DataBase.addUser;
import static core.db.DataBase.findUserById;
import static core.util.HttpRequestUtils.*;
import static core.util.IOUtils.readData;

public class RequestHandler extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RequestHandler.class);
    private Socket connection;

    public RequestHandler(Socket connectionSocket) {
        this.connection = connectionSocket;
    }

    public void run() {
        log.debug("New Client Connect! Connected IP: {}, Port: {}", connection.getInetAddress(), connection.getPort());

        try (InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {

            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();
            log.debug("request line = {}", line);

            if (line == null) {
                return;
            }

            String[] tokens = line.split(" ");
            String method = tokens[0];
            String url = tokens[1];

            Map<String, String> headerParameters = getHeaderParameters(br, line);
            byte[] body = new byte[0];
            DataOutputStream dos = new DataOutputStream(out);
            Map<String, String> parameters = new HashMap<>();

            if (method.equals("GET")) {
                if (url.equals("user/create")) {
                    int index = url.indexOf("?");

                    String requestPath = url.substring(0, index);
                    String queryString = url.substring(index + 1); // parameter 분리

                    parameters = parseQueryString(queryString);
                    Long userId = registerUser(parameters);
                } else if (url.equals("/user/list")) {

                    if (isLogin(headerParameters.get("Cookie"))) {
                        response302Header(dos, "/user/login.html", null);
                    }

                    url += ".html";
                }
                body = Files.readAllBytes(new File("./webapp" + url).toPath());
            } else if (method.equals("POST")) {
                String queryString = readData(br, Integer.parseInt(headerParameters.get("Content-Length")));
                log.debug("post data = {}", queryString);

                parameters = parseQueryString(queryString);

                if (url.equals("/user/create")) {
                    Long userId = registerUser(parameters);

                    if (userId != null) {
                        response302Header(dos, "/index.html", null);
                        return;
                    }
                } else if (url.equals("/user/login")) {
                    User user = findUserById(Long.parseLong(parameters.get("userId")));

                    if (user != null && (user.getPassword().equals(parameters.get("password")))) {
                        response302Header(dos, "/index.html", "logined=true");
                    } else {
                        responseResource(out, "/user/login_failed.html");
                    }
                    return;
                }
            }

            if (url.contains(".css")) {
                response200Header(dos, body.length, "text/css");
                responseBody(dos, body);
                return;
            }
            response200Header(dos, body.length, "text/html");
            responseBody(dos, body);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private Boolean isLogin(String line) {
        Map<String, String> cookies = parseCookies(line);
        String logined = cookies.get("logined");

        if (logined == null) {
            return false;
        }
        return Boolean.parseBoolean(logined);
    }

    private Long registerUser(Map<String, String> parameters) {
        User user = new User(Long.parseLong(parameters.get("userId")), parameters.get("password"), parameters.get("name"), parameters.get("email"));
        addUser(user);
        log.debug(user.toString());

        return Long.parseLong(parameters.get("userId"));
    }

    private Map<String, String> getHeaderParameters(BufferedReader br, String line) throws IOException {
        Map<String, String> headerParameters = new HashMap<>();

        while (!line.equals("")) {
            line = br.readLine();
            Pair pair = parseHeader(line);

            if (pair != null) {
                headerParameters.put(pair.getKey(), pair.getValue());
                log.debug("header: {} - {}", pair.getKey(), pair.getValue());
            }
        }
        return headerParameters;
    }

    private void responseResource(OutputStream out, String url) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] body = Files.readAllBytes(new File("./webapp" + url).toPath());

        response200Header(dos, body.length, "text/html");
        responseBody(dos, body);
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent, String type) {
        try {
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: " + type + ";charset=utf-8\r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void response302Header(DataOutputStream dos, String location, String cookie) {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect \r\n");
            dos.writeBytes("Location: " + location + "\r\n");

            if (cookie != null) {
                dos.writeBytes("Set-Cookie: " + cookie + "\r\n");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body) {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}

package core.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class HttpResponse {

    private static final Logger log = LoggerFactory.getLogger(HttpResponse.class);

    private String resourceDirectory = "./webapp/";
    private DataOutputStream dos;
    private Map<String, String> headers = new HashMap<>();

    public HttpResponse(OutputStream out) {
        dos = new DataOutputStream(out);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void forward(String url) throws IOException {
        byte[] body = Files.readAllBytes(new File(resourceDirectory + url).toPath());

        if(url.endsWith(".css")){
            headers.put("Content-Type", "text/css");
        } else if (url.endsWith(".js")) {
            headers.put("Content-Type", "application/javascript");
        } else {
            headers.put("Content-Type", "text/html");
        }
        headers.put("Content-Length", body.length + "");
        response200Header(body.length);
        responseBody(body);
    }

    public void response200Header(int lengthOfBodyContent) throws IOException {
        try {
            dos.writeBytes("HTTP/1.1 200 OK\r\n");
            processorHeader();
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void responseBody(byte[] body) throws IOException {
        try {
            dos.write(body, 0, body.length);
            dos.flush();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void sendRedirect(String location) throws IOException {
        try {
            dos.writeBytes("HTTP/1.1 302 Redirect\r\n");
            dos.writeBytes("Location: " + location + "\r\n");
            processorHeader();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void processorHeader() throws IOException {
        try {
            for (String key : headers.keySet()) {
                dos.writeBytes(key + ": " + headers.get(key) + ";");
            }
            dos.writeBytes("\r\n");
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

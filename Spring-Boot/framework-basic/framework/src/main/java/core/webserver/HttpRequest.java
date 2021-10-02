package core.webserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static core.util.HttpRequestUtils.*;
import static core.util.IOUtils.readData;

public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private String path;
    private HttpMethod method;
    private Map<String, String> header;
    private Map<String, String> params;
    private RequestLine requestLine;

    public String getPath() {
        return path;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getParams(String key) {
        return params.get(key);
    }

    public HttpRequest(InputStream in) throws IOException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line = br.readLine();

            requestLine = new RequestLine(line);
            path = requestLine.getPath();
            method = requestLine.getMethod();

            log.debug("path: {}", path);

            header = setHeader(br, line);
            if (requestLine.getMethod().equals(HttpMethod.POST)) {
                params = setParameter(br);
            } else {
                params = requestLine.getParams();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private Map<String, String> setHeader(BufferedReader br, String line) throws IOException {
        Map<String, String> headerParameters = new HashMap<>();

        while (!line.equals("")) {
            line = br.readLine();
            Pair pair = parseHeader(line);

            if (pair != null) {
                headerParameters.put(pair.getKey(), pair.getValue().trim());
                log.debug("header: {} - {}", pair.getKey(), pair.getValue());
            }
        }
        return headerParameters;
    }

    private Map<String, String> setParameter(BufferedReader br) throws IOException {
        return parseQueryString(readData(br, Integer.parseInt(header.get("Content-Length"))));
    }

    public String getDefaultPath(String path) {
        if (path.equals("/")){
            return "/index.html";
        }
        return path;
    }
}

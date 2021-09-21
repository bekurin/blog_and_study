package core.webserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import static core.util.HttpRequestUtils.*;
import static core.util.IOUtils.readData;

public class HttpRequest {

    private String method;
    private String path;
    private Map<String, String> header;
    private Map<String, String> parameter;

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getHeader(String key) {
        return header.get(key);
    }

    public String getParameter(String key) {
        return parameter.get(key);
    }

    public HttpRequest(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        String line = br.readLine();

        String[] tokens = line.split(" ");
        method = tokens[0];
        path = tokens[1];

        header = setHeader(br, line);
        parameter = setParameter(path, method, br);
    }

    private Map<String, String> setHeader(BufferedReader br, String line) throws IOException {
        Map<String, String> headerParameters = new HashMap<>();

        while (!line.equals("")) {
            line = br.readLine();
            Pair pair = parseHeader(line);

            if (pair != null) {
                headerParameters.put(pair.getKey(), pair.getValue().trim());
            }
        }
        return headerParameters;
    }

    private Map<String, String> setParameter(String path, String method, BufferedReader br) throws IOException {
        String queryString = "";

        if (method.equals("GET")){
            int index = path.indexOf("?");

            queryString = path.substring(index + 1);
            this.path = path.substring(0, index);
        } else if (method.equals("POST")) {
            queryString = readData(br, Integer.parseInt(header.get("Content-Length")));
        }

        return parseQueryString(queryString);
    }
}

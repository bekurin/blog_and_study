package core.util;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpRequestUtilsTest {
    @Test
    public void parseQueryString() {
        String queryString = "userId=javajigi";
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);
        assertEquals(parameters.get("userId"), "javajigi");
        assertEquals(parameters.get("password"), null);

        queryString = "userId=javajigi&password=password2";
        parameters = HttpRequestUtils.parseQueryString(queryString);
        assertEquals(parameters.get("userId"), "javajigi");
        assertEquals(parameters.get("password"), "password2");
    }
}
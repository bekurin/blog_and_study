package core.webserver;

import core.model.User;
import core.util.HttpRequestUtils;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestHandlerTest {

    @Test
    public void getParameters() {
        //given
        String url = "/user/create?userId=1&password=1234&name=james";
        int index = url.indexOf("?");

        //when
        String requestPath = url.substring(0, index);
        String queryString = url.substring(index + 1);

        //then
        Map<String, String> parameters = HttpRequestUtils.parseQueryString(queryString);

        User user = new User(Long.parseLong(parameters.get("userId")), parameters.get("password"), parameters.get("name"), "test");

        assertEquals("1234", user.getPassword());
        assertEquals(1L, user.getUserId());
        assertEquals("james", user.getName());
        assertEquals(requestPath, "/user/create");
    }
}
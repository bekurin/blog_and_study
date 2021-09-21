package core.webserver;

import core.model.User;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static core.util.HttpRequestUtils.parseCookies;
import static core.util.HttpRequestUtils.parseQueryString;
import static org.assertj.core.api.Assertions.assertThat;
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
        Map<String, String> parameters = parseQueryString(queryString);

        User user = new User(Long.parseLong(parameters.get("userId")), parameters.get("password"), parameters.get("name"), "test");

        assertEquals("1234", user.getPassword());
        assertEquals(1L, user.getUserId());
        assertEquals("james", user.getName());
        assertEquals(requestPath, "/user/create");
    }
    
    @Test
    public void getCookies() {
        //given
        String cookie = "null; logined=true; csrftoken=KpDCHpt5Rtc2CjJR2sxIrSwd3sUwxKgTNh1jpxWTWGiTAez93tdfKK5tam3BXffm; Idea-4b650776=ac4d62df-ed7c-4b68-b72c-f0b376d76ba8; Idea-95a1b1d3=f56f3111-d925-47e2-8912-63f994e4ac40";
        
        //when
        Map<String, String> cookies = parseCookies(cookie);

        //then
        assertThat(Boolean.parseBoolean(cookies.get("logined"))).isEqualTo(true);
    }
}
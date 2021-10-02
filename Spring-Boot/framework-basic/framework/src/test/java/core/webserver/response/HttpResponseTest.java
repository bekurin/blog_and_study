package core.webserver.response;

import core.webserver.HttpResponse;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class HttpResponseTest {
    private String testDirectory = "./src/test/java/core/webserver/response/";
    
    @Test
    public void responseForward() throws Exception {
        //given
        HttpResponse response = new HttpResponse(createOutputStream("Http_Forward.txt"));
        //when
        response.forward("/index.html");
    }
    
    @Test
    public void responseRedirect() throws Exception {
        //given
        HttpResponse response = new HttpResponse(createOutputStream("Http-Redirect.txt"));

        //when
        response.sendRedirect("/index.html");
    }

    @Test
    public void responseCookies() throws Exception {
        //given
        HttpResponse response = new HttpResponse(createOutputStream("Http-Cookie.txt"));

        //when
        response.addHeader("Set-Cookie", "logined=true");
        response.sendRedirect("/index.html");
    }

    private OutputStream createOutputStream(String filename) throws FileNotFoundException {
        return new FileOutputStream(new File(testDirectory + filename));
    }
}

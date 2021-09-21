package core.webserver;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class HttpRequestTest {
    private String testDirectory = "./src/test/java/core/webserver/";
    
    @Test
    public void requestGet() throws Exception {
        //given
        FileInputStream in = new FileInputStream(testDirectory + "Http-GET.txt");

        //when
        HttpRequest request = new HttpRequest(in);
        
        //then
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("1");
    }

    @Test
    public void requestPost() throws Exception {
        //given
        FileInputStream in = new FileInputStream(testDirectory + "Http-POST.txt");

        //when
        HttpRequest request = new HttpRequest(in);

        //then
        assertThat(request.getMethod()).isEqualTo("POST");
        assertThat(request.getPath()).isEqualTo("/user/create");
        assertThat(request.getHeader("Connection")).isEqualTo("keep-alive");
        assertThat(request.getParameter("userId")).isEqualTo("2");
    }
}

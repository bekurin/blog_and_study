package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() throws Exception {
        //given
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);

        //when
        NetworkClient client = ac.getBean(NetworkClient.class);

        //then
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {

        @Bean
        public NetworkClient networkClient() {
            System.out.println("LifeCycleConfig.networkClient");
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://localhost.com");
            return networkClient;
        }
    }
}

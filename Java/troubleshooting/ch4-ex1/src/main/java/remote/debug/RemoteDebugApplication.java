package remote.debug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RemoteDebugApplication {
    /**
     * java -jar -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=0.0.0.0:5005 ch4-ex1/build/libs/ch4-ex1.jar
     * TCP/IP 기반의 원격 디버깅 활성화 버전
     *
     * Remote Jvm Debug Connection 을 획득하여 연결하면 된다.
     * 중요한 점은 개발 환경의 디버그 앱 코드 버전과 로컬의 코드 버전이 같은지를 확인해야한다.
     * 만약, 코드 버전이 같지 않다면 엉뚱한 곳에서 디버그 모드가 잡힐 수 있다.
     *
     * 추가로, 운영 환경에서 위와 같이 원격 디버깅을 시도하고자 한다면 개인 정보 유출과 비정상적인 서비스 종료 --> 장애로 이어질 수 있으니 유의하도록 하자.
     *
     * 원격 디버깅 기술은 특정 버전에 한해서 문제가 발생하는 경우 사용하면 유용하다.
     * ex. QA 환경에서만 안돼요. 개발 환경에서만 안돼요.
     */
    public static void main(String[] args) {
        SpringApplication.run(RemoteDebugApplication.class, args);
    }
}

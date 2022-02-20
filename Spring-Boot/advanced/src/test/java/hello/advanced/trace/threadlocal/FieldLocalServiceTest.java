package hello.advanced.trace.threadlocal;

import hello.advanced.trace.threadlocal.code.FieldLocalService;
import hello.advanced.trace.threadlocal.code.FieldService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class FieldLocalServiceTest {

    private FieldLocalService fieldLocalService = new FieldLocalService();

    @Test
    void field() {
        log.info("main start");
        Runnable userA = () -> {
            fieldLocalService.logic("userA");
        };

        Runnable userB = () -> {
            fieldLocalService.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");

        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");

        threadA.start();
        sleep(100);

        threadB.start();
        sleep(2000);
        log.info("main exit");
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

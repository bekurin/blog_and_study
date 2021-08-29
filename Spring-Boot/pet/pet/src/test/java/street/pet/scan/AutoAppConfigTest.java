package street.pet.scan;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.service.OwnerService;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class AutoAppConfigTest {

    @Test
    @DisplayName("AutoAppConfig 동작 확인")
    public void basicScan() throws Exception {
        //given
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        //when
        OwnerService ownerService = ac.getBean(OwnerService.class);

        //then
        assertThat(ownerService).isInstanceOf(OwnerService.class);
    }

    @Test
    @DisplayName("Object 타입으로 모두 조회")
    public void findAllBeanByObjectType() throws Exception {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();

        //when
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName + ", beanDefinition = " + beanDefinition);
            }
        }
    }
}

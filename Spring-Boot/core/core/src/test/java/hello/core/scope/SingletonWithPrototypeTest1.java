package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    public void prototypeFind() throws Exception {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        //when
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        prototypeBean1.addCount();
        prototypeBean2.addCount();

        //then
        assertThat(prototypeBean1.getCount()).isEqualTo(1);
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    public void singletonClientUserPrototype() throws Exception {
        //given
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        //when
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);

        int count1 = clientBean1.logic();
        int count2 = clientBean2.logic();

        //then
        assertThat(count1).isEqualTo(1);
        assertThat(count2).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {

        // 생성 시점에 주입된 prototypeBean을 계속 사용한다.
        // private final PrototypeBean prototypeBean;

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;
        // private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        // private ObjectFactory<PrototypeBean> prototypeBeanProvider;

        /*
        @Autowired
        public ClientBean(PrototypeBean prototypeBean){
            this.prototypeBean = prototypeBean;
        }
        */

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count;

        public int getCount() {
            return count;
        }

        public void addCount() {
            this.count += 1;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy " + this);
        }
    }
}

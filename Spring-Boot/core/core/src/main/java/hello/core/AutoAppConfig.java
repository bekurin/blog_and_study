package hello.core;

import hello.core.member.MemoryMemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "hello.core", // ComponentScan 시작 위치 설정
        basePackageClasses = AutoAppConfig.class, // ComponentScan 시작 클래스 설정
        // default는 AutoAppConfig 위치부터 시작하여 scan 한다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
) // <-- @Component가 붙은 클래스를 자동으로 찾아가서 스프링 빈에 등록해준다.
public class AutoAppConfig {

    @Bean
    public MemoryMemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}

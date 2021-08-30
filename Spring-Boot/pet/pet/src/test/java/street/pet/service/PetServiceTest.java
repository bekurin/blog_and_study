package street.pet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.repository.PetRepository;

import static org.junit.Assert.*;

public class PetServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final PetService petService = ac.getBean(PetService.class);
    private final PetRepository petRepository = ac.getBean(PetRepository.class);

    @Test
    @DisplayName("반려동물 등록 확인")
    public void register() throws Exception {
        //given

        //when

        //then
    }
}
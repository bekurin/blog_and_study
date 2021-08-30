package street.pet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.Vet;
import street.pet.repository.VetRepository;

import static org.assertj.core.api.Assertions.assertThat;

public class VetServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final VetRepository vetRepository = ac.getBean(VetRepository.class);
    private final VetService vetService = ac.getBean(VetService.class);

    @Test
    @DisplayName("Vet 저장하기")
    public void saveVet() throws Exception {
        //given
        Vet vet = Vet.builder().name("vetA").major("test major").build();

        //when
        Long vetId = vetService.saveVet(vet);

        //then
        assertThat(vetRepository.findOne(vetId).getName()).isEqualTo("vetA");
    }

    @Test
    @DisplayName("업데이트 Vet")
    public void updateMajor() throws Exception {
        //given
        Vet vet = Vet.builder().name("vetA").major("test major").build();
        String updateMajor = "test2 Major";

        //when
        Long vetId = vetService.saveVet(vet);
        vetService.updateVet(vetId, updateMajor);

        //then
        assertThat(vetRepository.findOne(vetId).getMajor()).isEqualTo(updateMajor);
    }

    @Test
    @DisplayName("전체 vet 조회하기")
    public void findAllVet() throws Exception {
        //given
        Vet vetA = Vet.builder().name("vetA").major("test major").build();
        Vet vetB = Vet.builder().name("vetB").major("test major").build();

        //when
        Long vetAId = vetService.saveVet(vetA);
        Long vetBId = vetService.saveVet(vetB);

        //then
        assertThat(vetRepository.findAll().size()).isEqualTo(2);
    }
}
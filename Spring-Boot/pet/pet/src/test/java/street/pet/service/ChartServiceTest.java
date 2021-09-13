package street.pet.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.*;
import street.pet.repository.ChartRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ChartServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final ChartService chartService = ac.getBean(ChartService.class);
    private final ChartRepository chartRepository = ac.getBean(ChartRepository.class);
    private final MemberService memberService = ac.getBean(MemberService.class);
    private final PetService petService = ac.getBean(PetService.class);
    private final DepartmentService departmentService = ac.getBean(DepartmentService.class);
    private final VetService vetService = ac.getBean(VetService.class);

    @Test
    @DisplayName("차트 접수")
    public void chart() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        memberService.join(member);
        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("안과");
        departmentService.save(department);
        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);

        //when
        Long vetId = vetService.saveVet(vet);
        Long petId = petService.join(pet);
        Long id = chartService.Chart(petId, vetId);

        //then
        List<Chart> charts = chartRepository.findByIdWithPetVet(id);
        assertThat(charts.get(0).getVet().getName()).isEqualTo(vet.getName());
        assertThat(charts.get(0).getPet().getName()).isEqualTo(pet.getName());
    }

    @Test
    @DisplayName("차트 취소")
    public void cancel() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        memberService.join(member);
        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("신경외과");
        departmentService.save(department);
        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);

        //when
        Long vetId = vetService.saveVet(vet);
        Long petId = petService.join(pet);
        Long chartId = chartService.Chart(petId, vetId);

        //then
        Chart chart = chartService.findOne(chartId);
        chart.cancel();
        assertThat(chart.getStatus()).isEqualTo(ChartStatus.CANCEL);
    }
}
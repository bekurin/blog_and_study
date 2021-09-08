package street.pet.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.*;
import street.pet.repository.PrescriptionRepository;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PrescriptionServiceTest {

    private final ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final PrescriptionService prescriptionService = ac.getBean(PrescriptionService.class);
    private final PrescriptionRepository prescriptionRepository = ac.getBean(PrescriptionRepository.class);
    private final ChartService chartService = ac.getBean(ChartService.class);
    private final MemberService memberService = ac.getBean(MemberService.class);
    private final PetService petService = ac.getBean(PetService.class);
    private final DepartmentService departmentService = ac.getBean(DepartmentService.class);
    private final VetService vetService = ac.getBean(VetService.class);


    @Test
    @DisplayName("처방전 생성 O")
    public void create() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        memberService.join(member);
        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("신경외과");
        departmentService.save(department);
        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);

        Long vetId = vetService.saveVet(vet);
        Long petId = petService.join(pet);
        Long chartId = chartService.Chart(petId, vetId);

        //when
        Long id = prescriptionService.save(chartId, vetId, "타이레놀 1정");

        //then
        List<Prescription> prescriptions = prescriptionRepository.findAllWithChartVet(0, 100);
        assertThat(prescriptionService.findOne(id).getDescription()).isEqualTo("타이레놀 1정");
        assertThat(prescriptions.size()).isEqualTo(1);
        assertThat(prescriptions.get(0).getVet().getId()).isEqualTo(vetService.findOne(vetId).getId());
        assertThat(prescriptions.get(0).getChart().getId()).isEqualTo(chartService.findOne(chartId).getId());
    }

    @Test
    @DisplayName("처방전 전체 조회")
    public void findAll() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        memberService.join(member);
        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("신경외과");
        departmentService.save(department);
        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);

        Long vetId = vetService.saveVet(vet);
        Long petId = petService.join(pet);
        Long chartId = chartService.Chart(petId, vetId);

        //when
        prescriptionService.save(chartId, vetId, "테스트 처방전1");
        prescriptionService.save(chartId, vetId, "테스트 처방전2");

        //then
        assertThat(prescriptionService.findPrescriptions().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("chart로 처방전 조회")
    public void findByChart() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        memberService.join(member);
        Pet pet = Pet.createPet("하늘", LocalDate.of(2019, 8, 13), member);

        Department department = Department.createDepartment("신경외과");
        departmentService.save(department);
        Vet vet = Vet.createVet("안녕하세요 아무개입니다.", "아무개", department);

        Long vetId = vetService.saveVet(vet);
        Long petId = petService.join(pet);
        Long chartIdA = chartService.Chart(petId, vetId);
        Long chartIdB = chartService.Chart(petId, vetId);

        //when
        prescriptionService.save(chartIdA, vetId, "테스트 처방전1");
        prescriptionService.save(chartIdA, vetId, "테스트 처방전2");
        prescriptionService.save(chartIdB, vetId, "테스트 처방전3");

        //then
        assertThat(prescriptionService.findByChart(chartIdB).size()).isEqualTo(1);
        assertThat(prescriptionService.findByChart(chartIdA).size()).isEqualTo(2);
    }
}
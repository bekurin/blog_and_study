package street.pet.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class DomainTest {

    @Test
    @DisplayName("Member 생성 테스트")
    public void createMember() throws Exception {
        //given
        Address address = createAddress("서울", "테스트", "123-4");

        //when
        Member member = createMember("userA", "123-456-789", address);

        //then
        assertThat(member.getName()).isEqualTo("userA");
    }


    @Test
    @DisplayName("Pet 생성 테스트")
    public void createPet() throws Exception {
        //given
        Address address = createAddress("경기", "테스트", "453-4");
        Member member = createMember("userB", "543-654-2345", address);

        //when
        Pet pet = createPet("petA", LocalDate.of(2020, 9, 3), member);

        //then
        assertThat(pet.getName()).isEqualTo("petA");
        assertThat(pet.getMember()).isEqualTo(member);

        assertThat(pet.getMember().getPets().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Vet 생성 테스트")
    public void createVet() throws Exception {
        //given
        Department department = Department.createDepartment("신경외과");

        //when
        Vet vet = createVet("안녕하세요 신경외과 전문의입니다.", "박성준", department);

        //then
        assertThat(vet.getName()).isEqualTo("박성준");
        assertThat(vet.getDepartment().getVets().size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Chart 생성 테스트")
    public void createChart() throws Exception {
        //given
        Address address = createAddress("경기", "테스트", "543-4");
        Member member = createMember("김하준", "973-614-2124", address);

        Pet pet = createPet("petC", LocalDate.of(2020, 9, 3), member);

        Department department = Department.createDepartment("신경외과");
        Vet vet = createVet("안녕하세요 신경외과 전문의입니다.", "조태경", department);


        //when
        Chart chart = Chart.createChart(vet, pet);

        //then
        assertThat(chart.getPet()).isEqualTo(pet);
        assertThat(chart.getPet().getCharts().size()).isEqualTo(1);

        assertThat(chart.getVet()).isEqualTo(vet);
        assertThat(chart.getVet().getCharts().size()).isEqualTo(1);

        assertThat(chart.getStatus()).isEqualTo(ChartStatus.READY);
    }

    @Test
    @DisplayName("Prescription 생성 테스트")
    public void createPrescription() throws Exception {
        //given
        Address address = createAddress("경기", "테스트", "543-4");
        Member member = createMember("김하준", "973-614-2124", address);

        Pet pet = createPet("구름", LocalDate.of(2020, 9, 3), member);

        Department department = Department.createDepartment("신경외과");
        Vet vetA = createVet("안녕하세요 신경외과 전문의입니다.", "조태경", department);
        Vet vetB = createVet("안녕하세요 신경외과 전문의입니다.", "하준석", department);

        Chart chart = Chart.createChart(vetA, pet);

        //when
        Prescription prescriptionA = createPrescription("처방 내역 1", chart, vetA);
        Prescription prescriptionB = createPrescription("처방 내역 2", chart, vetB);

        //then
        assertThat(chart.getPrescriptions().size()).isEqualTo(2);
        assertThat(prescriptionA.getChart()).isEqualTo(chart);
        assertThat(prescriptionA.getDescription()).isEqualTo(prescriptionA.getDescription());

        assertThat(prescriptionA.getVet()).isEqualTo(vetA);
        assertThat(prescriptionB.getVet()).isEqualTo(vetB);
    }

    //== 생성 메서드 ==//
    private Member createMember(String name, String phone, Address address) {
        return Member.createMember(name, phone, address);
    }

    private Address createAddress(String city, String street, String zipcode) {
        return new Address(city, street, zipcode);
    }

    private Pet createPet(String name, LocalDate birthDate, Member member) {
        return Pet.createPet(name, birthDate, member);
    }

    private Vet createVet(String description, String name, Department department) {
        return Vet.createVet(description, name, department);
    }

    private Prescription createPrescription(String description, Chart chart, Vet vet) {
        return Prescription.createPrescription(description, chart, vet);
    }
}

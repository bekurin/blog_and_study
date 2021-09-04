package street.pet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.Department;
import street.pet.domain.Vet;

import static org.assertj.core.api.Assertions.assertThat;

public class VetServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final VetService vetService = ac.getBean(VetService.class);
    private final DepartmentService departmentService = ac.getBean(DepartmentService.class);

    @Test
    @DisplayName("수의사 저장 테스트 O")
    public void join() throws Exception {
        //given
        Department department = Department.createDepartment("신경외과");
        departmentService.save(department);

        Vet vet = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);

        //when
        Long vetId = vetService.saveVet(vet);

        //then
        assertThat(vetService.findOne(vetId).getName()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("수의사 저장 테스트 X - 진료과목 등록 X")
    public void joinFail() throws Exception {
        //given
        Department department = Department.createDepartment("신경외과");

        Vet vet = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);

        //when
        Assertions.assertThrows(IllegalStateException.class,
                () -> vetService.saveVet(vet));

        //then
    }

    @Test
    @DisplayName("수의사 저장 테스트 X - 중복이름")
    public void joinFailByName() throws Exception {
        //given
        Department department = Department.createDepartment("신경외과");
        departmentService.save(department);

        Vet vetA = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);
        Vet vetB = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", department);

        //when
        vetService.saveVet(vetA);

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> vetService.saveVet(vetB));
    }

    @Test
    @DisplayName("수의사 전체 조회")
    public void findVets() throws Exception {
        //given
        Department departmentA = Department.createDepartment("신경외과");
        Department departmentB = Department.createDepartment("치과");
        departmentService.save(departmentA);
        departmentService.save(departmentB);

        //when
        Vet vetA = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", departmentA);
        Vet vetB = Vet.createVet("안녕하세요 홍길동입니다.", "아무개", departmentB);

        vetService.saveVet(vetA);
        vetService.saveVet(vetB);

        //then
        assertThat(vetService.findVets().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("수의사 부서 조회")
    public void findByDepartment() throws Exception {
        //given
        Department departmentA = Department.createDepartment("신경외과");
        Department departmentB = Department.createDepartment("치과");
        departmentService.save(departmentA);
        departmentService.save(departmentB);

        //when
        Vet vetA = Vet.createVet("안녕하세요 홍길동입니다.", "홍길동", departmentA);
        Vet vetB = Vet.createVet("안녕하세요 홍길동입니다.", "아무개", departmentB);
        Vet vetC = Vet.createVet("안녕하세요 홍길동입니다.", "풍산자", departmentB);

        vetService.saveVet(vetA);
        vetService.saveVet(vetB);
        vetService.saveVet(vetC);

        //then
        assertThat(vetService.findByDepartment(departmentA).size()).isEqualTo(1);
        assertThat(vetService.findByDepartment(departmentB).size()).isEqualTo(2);
    }



}
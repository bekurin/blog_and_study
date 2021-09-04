package street.pet.service;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.Department;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DepartmentServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final DepartmentService departmentService = ac.getBean(DepartmentService.class);

    @Test
    @DisplayName("진료과목 생성 O")
    public void save() throws Exception {
        //given
        Department department = Department.createDepartment("신경외과");

        //when
        Long id = departmentService.save(department);

        //then
        assertThat(departmentService.findOne(id).getName()).isEqualTo("신경외과");
    }

    @Test
    @DisplayName("진료과목 생성 X")
    public void saveFail() throws Exception {
        //given
        Department departmentA = Department.createDepartment("신경외과");
        Department departmentB = Department.createDepartment("신경외과");

        //when
        departmentService.save(departmentA);

        //then
        assertThrows(IllegalStateException.class,
                () -> departmentService.save(departmentB));
    }

    @Test
    @DisplayName("진료과목 전체 조회")
    public void findAll() throws Exception {
        //given
        Department departmentA = Department.createDepartment("신경외과");
        Department departmentB = Department.createDepartment("정신과");

        //when
        departmentService.save(departmentA);
        departmentService.save(departmentB);

        //then
        assertThat(departmentService.findDepartments().size()).isEqualTo(2);
    }
}
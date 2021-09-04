package street.pet.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Department extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "department_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "department")
    private List<Vet> vets = new ArrayList<>();

    //== 비즈니스 로직 ==//
    public static Department createDepartment(String name){
        Department department = new Department();
        department.name = name;
        return department;
    }
}

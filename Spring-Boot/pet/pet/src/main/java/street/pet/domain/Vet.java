package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vet extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "vet_id")
    private Long id;

    private String description;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    //== 연관관계 메서드 ==//
    public void setDepartment(Department department) {
        if (this.department != null) {
            this.department.getVets().remove(this);
        }
        this.department = department;
        department.getVets().add(this);
    }

    //== 비즈니스 로직 ==//
    public static Vet createVet(String description, String name, Department department) {
        Vet vet = new Vet();
        vet.description = description;
        vet.name = name;
        vet.setDepartment(department);

        return vet;
    }

    public void update(String description, Department department) {
        this.description = description;
        setDepartment(department);
    }
}

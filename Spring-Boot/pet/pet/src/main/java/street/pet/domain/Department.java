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
}

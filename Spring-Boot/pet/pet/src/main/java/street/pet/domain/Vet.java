package street.pet.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Vet extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "vet_id")
    private Long id;

    private String description;

    private String name;

    @OneToMany(mappedBy = "vet")
    private List<Chart> charts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id")
    private Department department;
}

package street.pet.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Vet extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "vet_id")
    private Long id;

    private String name;

    private String major;

    @ManyToMany(mappedBy = "vet", cascade = CascadeType.ALL)
    private List<MedicalCare> medicalCare = new ArrayList<>();
}

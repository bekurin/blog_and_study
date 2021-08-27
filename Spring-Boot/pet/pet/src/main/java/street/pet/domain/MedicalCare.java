package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MedicalCare extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "medical_care_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "medical_care_item",
            joinColumns = @JoinColumn(name = "medical_care_id"),
            inverseJoinColumns = @JoinColumn(name = "vet_id"))
    private List<Vet> vet = new ArrayList<>();
}

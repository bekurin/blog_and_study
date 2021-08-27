package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chart extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "hospitalization_id")
    private Long Id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "vet_id")
    private Vet vet;

    private String diseaseName;

    private String description;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private ChartStatus status; // READY, ADMISSION, DISCHARGE

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}

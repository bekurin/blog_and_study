package street.pet.domain;

import lombok.Getter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Chart {

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

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private ChartStatus status; // READY, ADMISSION, DISCHARGE
}

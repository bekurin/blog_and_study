package street.pet.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Prescription extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "prescription_id")
    private Long id;

    @OneToOne(mappedBy = "prescription", fetch = FetchType.LAZY)
    private Chart chart;

    private String description;
}

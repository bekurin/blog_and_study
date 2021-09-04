package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Prescription extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "prescription_id")
    private Long id;

    @OneToOne(mappedBy = "prescription", fetch = FetchType.LAZY)
    private Chart chart;

    private String description;

    //== 연관관계 메서드 ==//
    public void setChart(Chart chart) {
        this.chart = chart;
    }

    //== 비즈니스 로직 ==//
    public static Prescription createPrescription(String description){
        Prescription prescription = new Prescription();
        prescription.description = description;
        return prescription;
    }
}

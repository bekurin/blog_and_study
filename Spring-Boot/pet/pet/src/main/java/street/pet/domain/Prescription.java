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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chart_id")
    private Chart chart;

    private String description;

    //== 연관관계 메서드 ==//
    private void setChart(Chart chart){
        this.chart = chart;
        chart.getPrescription().add(this);
    }

    //== 비즈니스 로직 ==//
    public static Prescription createPrescription(String description, Chart chart){
        Prescription prescription = new Prescription();
        prescription.description = description;
        prescription.setChart(chart);
        return prescription;
    }
}

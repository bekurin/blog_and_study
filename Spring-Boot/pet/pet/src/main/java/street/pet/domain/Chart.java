package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chart extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChartStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    //== 연관관계 메서드 ==//
    public void setVet(Vet vet){
        this.vet = vet;
        vet.getCharts().add(this);
    }

    public void setPet(Pet pet){
        this.pet = pet;
        pet.getCharts().add(this);
    }

    public void setPrescription(Prescription prescription){
        this.prescription = prescription;
        prescription.setChart(this);
    }

    //== 비즈니스 로직 ==//
    public static Chart createChart(Vet vet, Pet pet, Prescription prescription){
        Chart chart = new Chart();
        chart.status = ChartStatus.READY;
        chart.setVet(vet);
        chart.setPet(pet);
        chart.setPrescription(prescription);
        return chart;
    }
}

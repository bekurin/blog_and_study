package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chart extends BaseTimeEntity {

    @Id
    @GeneratedValue
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

    @Enumerated(EnumType.STRING)
    private ChartStatus status; // READY, ADMISSION, DISCHARGE

    //== 연관관계 메서드 ==//
    public void setPet(Pet pet) {
        this.pet = pet;
        pet.getCharts().add(this);
    }

    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void setStatus(ChartStatus status) {
        this.status = status;
    }


    //== 비즈니스 로직 ==//
    public static Chart createChart(Pet pet, Vet vet, String diseaseName, String description) {
        Chart chart = new Chart();
        chart.setPet(pet);
        chart.setVet(vet);
        chart.setDiseaseName(diseaseName);
        chart.setDescription(description);
        chart.setStatus(ChartStatus.READY);
        return chart;
    }
}

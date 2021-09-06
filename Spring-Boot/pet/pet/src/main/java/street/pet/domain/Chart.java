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

    @OneToMany(mappedBy = "chart")
    private List<Prescription> prescriptions = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void setVet(Vet vet) {
        if (this.vet != null) {
            this.vet.getCharts().remove(this);
        }
        this.vet = vet;
        vet.getCharts().add(this);
    }

    public void setPet(Pet pet) {
        if (this.pet != null) {
            this.pet.getCharts().remove(this);
        }
        this.pet = pet;
        pet.getCharts().add(this);
    }

    //== 비즈니스 로직 ==//
    public static Chart createChart(Vet vet, Pet pet) {
        Chart chart = new Chart();
        chart.status = ChartStatus.READY;
        chart.setVet(vet);
        chart.setPet(pet);
        return chart;
    }

    public void cancel() {
        if (status != ChartStatus.READY) {
            throw new IllegalStateException("접수된 차트느느 취소가 불가능합니다.");
        }
        this.status = ChartStatus.CANCEL;
    }
}

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

    //== 비즈니스 로직 ==//
    public static Chart createChart(Vet vet, Pet pet) {
        Chart chart = new Chart();
        chart.status = ChartStatus.READY;
        chart.vet = vet;
        chart.pet = pet;
        return chart;
    }

    public void cancel() {
        if (status != ChartStatus.READY) {
            throw new IllegalStateException("접수가 완료된 차트는 취소가 불가능합니다.");
        }
        this.status = ChartStatus.CANCEL;
    }

    public void update(ChartStatus status){
        this.status = status;
    }
}

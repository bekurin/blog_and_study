package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "pet_id")
    private Long id;

    private LocalDate birthDate; // 생일

    private String name;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Chart> charts = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diagnosis_id")
    private Diagnosis diagnosis;

    @Enumerated(EnumType.STRING)
    private PetStatus status;

    //== 연관 관계 메서드 ==//
    void setOwner(Owner owner) {
        this.owner = owner;
        owner.getPets().add(this);
    }

    void setDiagnosis(Diagnosis diagnosis) {
        this.diagnosis = diagnosis;
        diagnosis.setPet(this);
    }

    void addChart(Chart chart) {
        charts.add(chart);
        chart.setPet(this);
    }

    //== 비즈니스 로직 ==//
    public static Pet createPet(Owner owner, Diagnosis diagnosis, LocalDate birthDate, String name, Chart... charts) {
        Pet pet = new Pet();
        pet.setOwner(owner);
        pet.setBirthDate(birthDate);
        pet.setName(name);
        pet.setStatus(PetStatus.DIAGNOSIS);

        for (Chart chart : charts) {
            pet.addChart(chart);
        }
        return pet;
    }

    /**
     * 진료 취소
     */
    public void cancel() {
        if (diagnosis.getStatus() == DiagnosisStatus.COMPLETE) {
            throw new IllegalStateException("진료가 끝난 예약은 취소가 불가능합니다.");
        }

        this.setStatus(PetStatus.CANCEL);
    }
}

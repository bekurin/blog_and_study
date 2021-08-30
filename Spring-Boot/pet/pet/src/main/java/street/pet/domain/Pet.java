package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseTimeEntity{

    @Id @GeneratedValue
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
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    //== 연관 관계 메서드 ==//
    void setOwner(Owner owner) {
        this.owner = owner;
        owner.getPet().add(this);
    }

    void setPetType(PetType petType) {
        this.petType = petType;
        petType.setPet(this);
    }

    void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    void addChart(Chart chart){
        charts.add(chart);
        chart.setPet(this);
    }

    //== 비즈니스 로직 ==//
    public static Pet createPet(Owner owner, PetType petType, LocalDate birthDate, Chart ...charts){
        Pet pet = new Pet();
        pet.setOwner(owner);
        pet.setPetType(petType);
        pet.setBirthDate(birthDate);

        for (Chart chart : charts) {
            pet.addChart(chart);
        }
        return pet;
    }

}

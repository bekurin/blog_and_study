package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PetType {

    @Id @GeneratedValue
    @Column(name = "pet_type_id")
    private Long id;

    private String description;

    @OneToOne(mappedBy = "petType", fetch = LAZY)
    private Pet pet;
}

package street.pet.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Owner extends BaseTimeEntity{

    @Id @GeneratedValue
    @Column(name = "owner_id")
    Long id;

    private String telephone;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "owner")
    private List<Pet> pet = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    @Builder
    public Owner(String name, Address address){
        this.name = name;
        this.address = address;
    }
}

package street.pet.domain;

import lombok.Getter;
import org.apache.tomcat.jni.Address;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String phone;

    @OneToMany(mappedBy = "member")
    private List<Pet> pets = new ArrayList<>();

    @Embedded
    private Address address;
}

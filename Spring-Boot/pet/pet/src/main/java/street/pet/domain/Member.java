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

    //== 비즈니스 로직 ==//
    public static Member crateMember(String name, String phone, Address address){
        Member member = new Member();
        member.name = name;
        member.phone = phone;
        member.address = address;

        return member;
    }
}

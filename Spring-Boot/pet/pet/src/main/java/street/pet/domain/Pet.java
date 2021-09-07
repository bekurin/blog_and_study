package street.pet.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "pet_id")
    private Long id;

    private String name;
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //== 연관관계 메서드 ==//
    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getPets().remove(this);
        }
        this.member = member;
        member.getPets().add(this);
    }

    //== 비즈니스 로직 ==//
    public static Pet createPet(String name, LocalDate birthDate, Member member) {
        Pet pet = new Pet();
        pet.name = name;
        pet.birthDate = birthDate;
        pet.setMember(member);

        return pet;
    }

    public void update(String name) {
        this.name = name;
    }
}

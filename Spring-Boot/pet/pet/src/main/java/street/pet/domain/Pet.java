package street.pet.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Pet extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "pet_id")
    private Long id;

    private String name;
    private LocalDateTime birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "pet")
    private List<Chart> charts = new ArrayList<>();
}

package street.pet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.Address;
import street.pet.domain.Member;
import street.pet.domain.Pet;
import street.pet.repository.PetRepository;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PetServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final PetService petService = ac.getBean(PetService.class);
    private final MemberService memberService = ac.getBean(MemberService.class);

    @Test
    @DisplayName("반려동물 join 테스트 O")
    public void petJoin() throws Exception {
        //given
        Address address = new Address("서울", "테스트", "123-4");
        Member member = Member.createMember("홍길동", "010-3232-4422", address);
        memberService.join(member);

        //when
        Pet pet = Pet.createPet("잔디", LocalDate.of(2020, 8, 1), member);
        Long petId = petService.join(pet);

        //then
        assertThat(petService.findOne(petId).getName()).isEqualTo("잔디");
    }

    @Test
    @DisplayName("반려동물 join 테스트 X")
    public void petJoinFail() throws Exception {
        //given
        Address address = new Address("부산", "바캉스", "123-4");
        Member member = Member.createMember("홍길동", "010-3232-4422", address);

        System.out.println("member.getId() = " + member.getId());
        //when
        Pet pet = Pet.createPet("잔디", LocalDate.of(2020, 8, 1), member);

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> petService.join(pet));
    }

    @Test
    @DisplayName("반려동물 전체 조회 테스트")
    public void findAll() throws Exception {
        //given
        Address address = new Address("경기", "테스트", "123-4");
        Member member = Member.createMember("홍길동", "010-3232-4422", address);
        memberService.join(member);

        //when
        Pet petA = Pet.createPet("잔디", LocalDate.of(2020, 8, 1), member);
        Pet petB = Pet.createPet("구름", LocalDate.of(2019, 4, 22), member);

        petService.join(petA);
        petService.join(petB);

        //then
        assertThat(petService.findPets().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원으로 반려동물 검색")
    public void findByMember() throws Exception {
        //given
        Address address = new Address("경기", "테스트", "123-4");
        Member member = Member.createMember("홍길동", "010-3232-4422", address);
        memberService.join(member);

        Pet petA = Pet.createPet("잔디", LocalDate.of(2020, 8, 1), member);
        Pet petB = Pet.createPet("구름", LocalDate.of(2019, 4, 22), member);

        //when
        petService.join(petA);
        petService.join(petB);

        //then
        assertThat(petService.findByMember(member).size()).isEqualTo(2);
    }

    @Test
    @DisplayName("반려동물 이름 수정 테스트")
    public void updateName() throws Exception {
        //given
        Address address = new Address("경기", "테스트", "123-4");
        Member member = Member.createMember("홍길동", "010-3232-4422", address);
        memberService.join(member);

        Pet pet = Pet.createPet("잔디", LocalDate.of(2020, 8, 1), member);

        //when
        Long petId = petService.join(pet);
        petService.updatePet(petId, "하늘");

        //then
        assertThat(petService.findOne(petId).getName()).isEqualTo("하늘");
    }
}
package street.pet.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import street.pet.AutoAppConfig;
import street.pet.domain.Address;
import street.pet.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;

public class MemberServiceTest {

    ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
    private final MemberService memberService = ac.getBean(MemberService.class);

    @Test
    @DisplayName("회원 join 테스트 O")
    public void memberJoin() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);

        //when
        Long memberId = memberService.join(member);

        //then
        assertThat(memberService.findOne(memberId).getName()).isEqualTo("홍길동");
        assertThat(memberService.findOne(memberId)).isInstanceOf(Member.class);
    }

    @Test
    @DisplayName("회원 join 테스트 X")
    public void memberJoinFail() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member memberA = Member.createMember("홍길동", "123-456-5484", address);
        Member memberB = Member.createMember("홍길동", "844-421-5432", address);

        //when
        memberService.join(memberA);

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> memberService.join(memberB));
    }

    @Test
    @DisplayName("회원 전체 조회 테스트")
    public void findAll() throws Exception {
        //given
        Address address = new Address("서울", "test", "644-9");
        Member memberA = Member.createMember("홍길동", "123-456-5484", address);
        Member memberB = Member.createMember("아무개", "844-421-5432", address);

        //when
        memberService.join(memberA);
        memberService.join(memberB);

        //then
        assertThat(memberService.findMembers().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("회원 수정 테스트")
    public void memberUpdate() throws Exception {
        //given
        Address address = new Address("서울", "test", "632-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        Long memberId = memberService.join(member);

        //when
        memberService.updateMember(memberId, "485-239-3498", address);

        //then
        assertThat(memberService.findOne(memberId).getPhone()).isEqualTo("485-239-3498");
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    public void deleteMember() throws Exception {
        //given
        Address address = new Address("서울", "test", "632-9");
        Member member = Member.createMember("홍길동", "123-456-5484", address);
        Long memberId = memberService.join(member);

        //when
        Long id = memberService.deleteMember(memberId);
        Member member1 = memberService.findOne(id);

        //then
        Assertions.assertThrows(NullPointerException.class,
                () -> memberService.findOne(id).getName());
    }
}
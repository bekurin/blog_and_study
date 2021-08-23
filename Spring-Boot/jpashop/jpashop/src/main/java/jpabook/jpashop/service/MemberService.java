package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 단순 조회에는 readOnly = true를 사용하는 것이 성능, 안정성면에서 좋다.
@RequiredArgsConstructor // final로 선언한 변수들을 자동으로 생성자를 만들어서 초기화해준다.
public class MemberService {

    private final MemberRepository memberRepository;

    /*
     테스트 코드 동작에 있어 수정이 필요할 경우가 있기 때문에 생성자를 통해 초기화를 진행해준다.
     최신 스프링 부트 버전은 생성자가 하나라면 해당 생성자에 자동으로 @Autowired를 붙여준다.
     public MemberService(MemberRepository memberRepository) {
         this.memberRepository = memberRepository;
     }
     */

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void update(Long id, String name){
        Member member = memberRepository.findById(id).get();
        member.setName(name);
    }
}

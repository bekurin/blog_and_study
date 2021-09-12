package street.pet.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import street.pet.domain.Address;
import street.pet.domain.Member;
import street.pet.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicatedMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicatedMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 회원 수정
     */
    @Transactional
    public Long updateMember(Long id, String phone, Address address) {
        Member member = memberRepository.findOne(id);
        member.updateMember(phone, address);

        return member.getId();
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public Long deleteMember(Long id) {
        Member member = memberRepository.findOne(id);
        if (!member.getPets().isEmpty()) {
            throw new IllegalStateException("반려동물이 존재하는 회원은 삭제할 수 없습니다.");
        }

        memberRepository.deleteByMember(member);
        return id;
    }
}

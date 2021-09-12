package street.pet.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import street.pet.domain.Address;
import street.pet.domain.Member;
import street.pet.repository.MemberRepository;
import street.pet.service.MemberService;
import street.pet.web.dto.MemberResponseDto;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController extends BaseApiController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    /**
     * 멤버 조회
     */
    @GetMapping("/api/v1/members")
    public Result membersV1() {
        List<Member> members = memberRepository.findAll();

        List<MemberResponseDto> result = members.stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/member")
    public MemberResponseDto memberV1(
            @RequestParam(value = "id", defaultValue = "0") Long id) {
        Member member = memberRepository.findOne(id);
        return new MemberResponseDto(member);
    }

    /**
     * 멤버 생성
     */
    @PostMapping("/api/v1/member")
    public CreateMemberResponse saveMemberV1(
            @RequestBody @Valid CreateMemberRequest request) {
        Member member = Member.createMember(request.getName(), request.getPhone(), request.getAddress());
        Long id = memberService.join(member);

        return new CreateMemberResponse(id);
    }

    /**
     * 멤버 수정
     */
    @PutMapping("/api/v1/member/{id}")
    public UpdateMemberResponse updateMemberV1(
            @PathVariable Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        Long memberId = memberService.updateMember(id, request.getPhone(), request.getAddress());
        Member member = memberService.findOne(memberId);

        return new UpdateMemberResponse(member.getId(), member.getPhone(), member.getAddress());
    }

    /**
     * 멤버 삭제
     */
    @DeleteMapping("/api/v1/member/{id}")
    public DeleteMemberResponse deleteMemberResponseV1(
            @PathVariable Long id) {
        Long memberId = memberService.deleteMember(id);

        return new DeleteMemberResponse(memberId);
    }

    /**
     * 멤버 생성 request, response
     */
    @Data
    static class CreateMemberRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String phone;
        private Address address;
    }

    @Data
    @AllArgsConstructor
    static class CreateMemberResponse {
        private Long id;
    }

    /**
     * 멤버 수정 request, response
     */
    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String phone;
        private Address address;
    }

    @Data
    static class UpdateMemberRequest {
        private String phone;
        private Address address;
    }

    /**
     * 멤버 삭제 response
     */
    @Data
    @AllArgsConstructor
    static class DeleteMemberResponse {
        private Long id;
    }
}

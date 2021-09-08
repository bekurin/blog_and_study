package street.pet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Member;
import street.pet.service.MemberService;
import street.pet.web.dto.MemberResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @GetMapping("/api/v1/members")
    public List<MemberResponseDto> membersV1() {
        List<Member> members = memberService.findMembers();
        List<MemberResponseDto> result = members.stream()
                .map(member -> new MemberResponseDto(member))
                .collect(Collectors.toList());
        return result;
    }
}

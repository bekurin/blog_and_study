package street.pet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Member;
import street.pet.repository.MemberRepository;
import street.pet.web.dto.MemberResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberApiController extends BaseApiController {

    private final MemberRepository memberRepository;

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
            @RequestParam(value = "id", defaultValue = "0") Long id){
        Member member = memberRepository.findOne(id);
        return new MemberResponseDto(member);
    }
}

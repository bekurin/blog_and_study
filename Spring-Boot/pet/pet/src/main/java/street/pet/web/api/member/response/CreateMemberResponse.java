package street.pet.web.api.member.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateMemberResponse {
    private Long id;
    private String name;
}

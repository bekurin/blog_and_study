package street.pet.web.api.member.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import street.pet.domain.Address;

@Data
@AllArgsConstructor
public class UpdateMemberResponse {
    private Long id;
    private String phone;
    private Address address;
}

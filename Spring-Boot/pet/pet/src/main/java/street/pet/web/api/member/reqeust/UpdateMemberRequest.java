package street.pet.web.api.member.reqeust;

import lombok.Data;
import street.pet.domain.Address;

@Data
public class UpdateMemberRequest {
    private Long id;
    private String phone;
    private Address address;
}

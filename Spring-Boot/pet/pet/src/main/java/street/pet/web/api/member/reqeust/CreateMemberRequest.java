package street.pet.web.api.member.reqeust;

import lombok.Data;
import street.pet.domain.Address;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateMemberRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String phone;
    private Address address;
}

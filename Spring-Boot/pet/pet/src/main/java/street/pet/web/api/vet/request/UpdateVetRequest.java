package street.pet.web.api.vet.request;

import lombok.Data;

@Data
public class UpdateVetRequest {
    private String description;
    private Long departmentId;
}

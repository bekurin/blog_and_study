package street.pet.web.api.vet.request;

import lombok.Data;

@Data
public class CreateVetRequest {
    private String name;
    private String description;
    private Long departmentId;
}

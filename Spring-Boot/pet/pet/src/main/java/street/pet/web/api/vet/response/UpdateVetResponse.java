package street.pet.web.api.vet.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateVetResponse {
    private Long id;
    private String description;
    private String departmentName;
}

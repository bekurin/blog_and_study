package street.pet.web.api.vet.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateVetResponse {
    private Long id;
    private String name;
}

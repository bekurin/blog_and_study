package street.pet.web.api.pet.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreatePetResponse {
    private Long id;
    private String name;
}

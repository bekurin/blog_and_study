package street.pet.web.api.pet.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePetResponse {
    private Long id;
    private String name;
}

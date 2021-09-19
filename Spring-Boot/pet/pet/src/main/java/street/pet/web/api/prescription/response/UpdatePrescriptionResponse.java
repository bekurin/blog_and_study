package street.pet.web.api.prescription.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePrescriptionResponse {
    private Long id;
    private String description;
}

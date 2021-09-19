package street.pet.web.api.prescription.request;

import lombok.Data;

@Data
public class CreatePrescriptionRequest {
    private String description;
    private Long vetId;
    private Long chartId;
}

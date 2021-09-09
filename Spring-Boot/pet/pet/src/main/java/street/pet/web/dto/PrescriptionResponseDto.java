package street.pet.web.dto;

import lombok.Data;
import street.pet.domain.Prescription;

import java.time.LocalDateTime;

@Data
public class PrescriptionResponseDto {

    private String description;
    private VetResponseDto vet;
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    public PrescriptionResponseDto(Prescription entity) {
        description = entity.getDescription();
        vet = new VetResponseDto(entity.getVet());
        create_date = entity.getCreateDate();
        update_date = entity.getUpdateDate();
    }
}

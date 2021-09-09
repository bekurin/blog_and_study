package street.pet.web.dto;

import lombok.Data;
import street.pet.domain.Prescription;

import java.time.LocalDateTime;

@Data
public class PrescriptionResponseDto {

    private String description;
    private String doctor;
    private LocalDateTime create_date;
    private LocalDateTime update_date;

    public PrescriptionResponseDto(Prescription entity) {
        doctor = entity.getVet().getName();
        description = entity.getDescription();
        create_date = entity.getCreateDate();
        update_date = entity.getUpdateDate();
    }
}

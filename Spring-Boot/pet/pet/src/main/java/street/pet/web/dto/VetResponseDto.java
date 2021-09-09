package street.pet.web.dto;

import lombok.Data;
import street.pet.domain.Vet;

@Data
public class VetResponseDto {

    private String doctor;
    private String departmentName;

    public VetResponseDto(Vet entity) {
        doctor = entity.getName();
        departmentName = entity.getDepartment().getName();
    }
}
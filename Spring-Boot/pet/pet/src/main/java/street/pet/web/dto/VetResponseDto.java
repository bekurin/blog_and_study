package street.pet.web.dto;

import lombok.Data;
import street.pet.domain.Vet;

@Data
public class VetResponseDto {

    private Long id;
    private String doctor;
    private String departmentName;

    public VetResponseDto(Vet entity) {
        id = entity.getId();
        doctor = entity.getName();
        departmentName = entity.getDepartment().getName();
    }
}
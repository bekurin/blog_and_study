package street.pet.web.dto;

import lombok.Data;
import street.pet.domain.Vet;

@Data
public class VetResponseDto {

    private String name;
    private String departmentName;

    public VetResponseDto(Vet entity) {
        name = entity.getName();
        departmentName = entity.getDepartment().getName();
    }
}
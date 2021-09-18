package street.pet.web.dto;

import lombok.Data;
import street.pet.domain.Department;
import street.pet.domain.Vet;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DepartmentResponseDto {

    private Long id;
    private String name;
    private List<DepartmentVetResponseDto> vets;

    public DepartmentResponseDto(Department entity) {
        id = entity.getId();
        name = entity.getName();
        vets = entity.getVets().stream()
                .map(vet -> new DepartmentVetResponseDto(vet))
                .collect(Collectors.toList());
    }

    @Data
    static class DepartmentVetResponseDto {
        private Long id;
        private String name;

        public DepartmentVetResponseDto(Vet entity){
            id = entity.getId();
            name = entity.getName();
        }
    }
}

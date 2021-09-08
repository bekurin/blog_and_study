package street.pet.web.dto;

import street.pet.domain.Department;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentResponseDto {

    private Long id;
    private String name;
    private List<VetResponseDto> vets;

    public DepartmentResponseDto(Department entity) {
        id = entity.getId();
        name = entity.getName();
        vets = entity.getVets().stream()
                .map(vet -> new VetResponseDto(vet))
                .collect(Collectors.toList());
    }
}

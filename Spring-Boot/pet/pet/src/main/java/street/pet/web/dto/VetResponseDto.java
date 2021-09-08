package street.pet.web.dto;

import street.pet.domain.Vet;

public class VetResponseDto {

    private Long id;
    private String description;
    private String name;
    private String departmentName;

    public VetResponseDto(Vet entity) {
        id = entity.getId();
        description = entity.getDescription();
        name = entity.getName();

        //[!] 영속성 컨테스트 문제 가능성 존재
        departmentName = entity.getDepartment().getName();
    }
}

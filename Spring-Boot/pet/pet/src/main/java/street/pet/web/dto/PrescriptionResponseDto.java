package street.pet.web.dto;

import street.pet.domain.Prescription;

public class PrescriptionResponseDto {

    private Long id;
    private String description;
    private VetResponseDto vet;

    public PrescriptionResponseDto(Prescription entity) {
        id = entity.getId();
        description = entity.getDescription();
        vet = new VetResponseDto(entity.getVet());
        //[!] chart에서 호출하기 때문에 chart를 따로 저장하지 않음
    }
}

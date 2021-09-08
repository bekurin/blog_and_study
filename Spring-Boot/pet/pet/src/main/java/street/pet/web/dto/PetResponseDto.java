package street.pet.web.dto;

import street.pet.domain.Pet;

import java.time.LocalDate;

public class PetResponseDto {

    private Long id;
    private String name;
    private LocalDate birthDate;

    public PetResponseDto(Pet entity) {
        id = entity.getId();
        name = entity.getName();
        birthDate = entity.getBirthDate();
    }
}

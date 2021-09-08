package street.pet.web.dto;

import street.pet.domain.Address;
import street.pet.domain.Member;

import java.util.List;
import java.util.stream.Collectors;

public class MemberResponseDto {

    private Long id;
    private String name;
    private String phone;
    private Address address;
    private List<PetResponseDto> pets;

    public MemberResponseDto(Member entity) {
        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        address = entity.getAddress();
        pets = entity.getPets().stream()
                .map(pet -> new PetResponseDto(pet))
                .collect(Collectors.toList());
    }
}

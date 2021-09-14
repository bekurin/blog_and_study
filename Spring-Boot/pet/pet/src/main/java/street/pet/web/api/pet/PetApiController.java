package street.pet.web.api.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import street.pet.domain.Member;
import street.pet.domain.Pet;
import street.pet.repository.PetRepository;
import street.pet.service.MemberService;
import street.pet.service.PetService;
import street.pet.web.api.BaseApiController;
import street.pet.web.api.pet.request.CreatePetRequest;
import street.pet.web.api.pet.request.UpdatePetRequest;
import street.pet.web.api.pet.response.CreatePetResponse;
import street.pet.web.api.pet.response.DeletePetResponse;
import street.pet.web.api.pet.response.UpdatePetResponse;
import street.pet.web.dto.PetResponseDto;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PetApiController extends BaseApiController {

    private final PetRepository petRepository;
    private final MemberService memberService;
    private final PetService petService;

    /**
     * 반려동물 조회
     */
    @GetMapping("/api/v1/pets")
    public Result petsV1() throws NotFoundException {
        List<Pet> pets = petRepository.findAll();
        List<PetResponseDto> result = pets.stream()
                .map(pet -> new PetResponseDto(pet))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/pet")
    public PetResponseDto petV1(
            @RequestParam(value = "id") Long id) {
        Pet pet = petRepository.findOne(id);
        return new PetResponseDto(pet);
    }

    /**
     * 반려동물 생성
     */
    @PostMapping("/api/v1/pet")
    public CreatePetResponse cratePetV1(
            @RequestBody @Valid CreatePetRequest request) {
        Member member = memberService.findOne(request.getMemberId());

        Pet pet = Pet.createPet(request.getName(), request.getBirthDate(), member);
        Long petId = petService.join(pet);

        return new CreatePetResponse(petId, pet.getName());
    }

    /**
     * 반려동물 수정
     */
    @PutMapping("/api/v1/pet/{id}")
    public UpdatePetResponse updatePetV1(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePetRequest request) {
        Long petId = petService.updatePet(id, request.getName());
        Pet pet = petService.findOne(petId);

        return new UpdatePetResponse(pet.getId(), pet.getName());
    }

    /**
     * 반려동물 삭제
     */
    @DeleteMapping("/api/v1/pet/{id}")
    public DeletePetResponse deletePetV1(
            @PathVariable Long id) {
        //!! 연관관계(member, chart) 제거 후 pet 제거 수정 필요
        Long petId = petService.deletePet(id);
        return new DeletePetResponse(petId);
    }
}

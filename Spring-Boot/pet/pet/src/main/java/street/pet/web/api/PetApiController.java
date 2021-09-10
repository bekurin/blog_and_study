package street.pet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Pet;
import street.pet.repository.PetRepository;
import street.pet.web.dto.PetResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PetApiController extends BaseApiController{

    private final PetRepository petRepository;

    @GetMapping("/api/v1/pets")
    public Result petsV1(){
        List<Pet> pets = petRepository.findAll();
        List<PetResponseDto> result = pets.stream()
                .map(pet -> new PetResponseDto(pet))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/pet")
    public PetResponseDto petV1(
            @RequestParam(value = "id") Long id){
        Pet pet = petRepository.findOne(id);
        return new PetResponseDto(pet);
    }
}

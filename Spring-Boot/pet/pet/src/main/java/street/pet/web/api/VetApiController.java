package street.pet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Vet;
import street.pet.repository.VetRepository;
import street.pet.web.dto.VetResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VetApiController extends BaseApiController {

    private final VetRepository vetRepository;

    @GetMapping("/api/v1/vets")
    public Result vetsV1() {
        List<Vet> vets = vetRepository.findAllWithDepartment();
        List<VetResponseDto> result = vets.stream()
                .map(vet -> new VetResponseDto(vet))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/vet")
    public Result vetV1(
            @RequestParam(name = "id", defaultValue = "0") Long id) {
        List<Vet> vets = vetRepository.findByIdWithDepartment(id);
        List<VetResponseDto> result = vets.stream()
                .map(vet -> new VetResponseDto(vet))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }
}

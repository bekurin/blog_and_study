package street.pet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Prescription;
import street.pet.repository.PrescriptionRepository;
import street.pet.web.dto.PrescriptionResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PrescriptionApiController extends BaseApiController {

    private final PrescriptionRepository prescriptionRepository;

    @GetMapping("/api/v1/prescriptions")
    public Result prescriptionsV1(
            @RequestParam(name = "offset", defaultValue = "0") int offset,
            @RequestParam(name = "limit", defaultValue = "0") int limit) {
        List<Prescription> prescriptions = prescriptionRepository.findAllWithChartVet(offset, limit);
        List<PrescriptionResponseDto> result = prescriptions.stream()
                .map(prescription -> new PrescriptionResponseDto(prescription))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }
}

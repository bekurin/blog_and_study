package street.pet.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import street.pet.domain.Department;
import street.pet.domain.Vet;
import street.pet.repository.VetRepository;
import street.pet.service.DepartmentService;
import street.pet.service.VetService;
import street.pet.web.dto.VetResponseDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class VetApiController extends BaseApiController {

    private final VetRepository vetRepository;
    private final VetService vetService;
    private final DepartmentService departmentService;

    /**
     * 수의사 조회
     */
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

    /**
     * 수의사 생성
     */
    @PostMapping("/api/v1/vet")
    public CreateVetResponse createVetV1(
            @RequestBody @Valid CreateVetRequest request) {
        Department department = departmentService.findOne(request.getDepartmentId());
        Vet vet = Vet.createVet(request.getDescription(), request.getName(), department);
        Long vetId = vetService.saveVet(vet);

        return new CreateVetResponse(vetId, vet.getName());
    }

    /**
     * 수의사 수정
     */
    @PutMapping("/api/v1/vet/{id}")
    public UpdateVetResponse updateVetV1(
            @PathVariable Long id,
            @RequestBody @Valid UpdateVetRequest request){
        Department department = departmentService.findOne(request.getDepartmentId());
        Long vetId = vetService.updateVet(id, request.getDescription(), department);
        Vet vet = vetService.findOne(vetId);

        return new UpdateVetResponse(vetId, vet.getDescription(), department.getName());
    }

    /**
     * 수의사 생성 request, response
     */
    @Data
    @AllArgsConstructor
    static class CreateVetResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateVetRequest {
        private String name;
        private String description;
        private Long departmentId;
    }

    /**
     * 수의사 수정 request, response
     */
    @Data
    @AllArgsConstructor
    static class UpdateVetResponse {
        private Long id;
        private String description;
        private String departmentName;
    }

    @Data
    static class UpdateVetRequest {
        private String description;
        private Long departmentId;
    }
}

package street.pet.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import street.pet.domain.Prescription;
import street.pet.repository.PrescriptionRepository;
import street.pet.service.PrescriptionService;
import street.pet.web.dto.PrescriptionResponseDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PrescriptionApiController extends BaseApiController{

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionService prescriptionService;

    /**
     * 처방전 조회
     */
    @GetMapping("/api/v1/prescriptions")
    public Result prescriptionsV1() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();

        List<PrescriptionResponseDto> result = prescriptions.stream()
                .map(prescription -> new PrescriptionResponseDto(prescription))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/prescription")
    public PrescriptionResponseDto prescriptionV1(
            @RequestParam(name = "id") Long id) {
        Prescription prescription = prescriptionRepository.findOne(id);
        return new PrescriptionResponseDto(prescription);
    }

    /**
     * 처방전 생성
     */
    @PostMapping("/api/v1/prescription")
    public CreatePrescriptionResponse createPrescriptionV1(
            @RequestBody @Valid CreatePrescriptionRequest request) {
        Long id = prescriptionService.save(request.getChartId(), request.getVetId(), request.getDescription());
        Prescription prescription = prescriptionService.findOne(id);

        return new CreatePrescriptionResponse(prescription.getId(), prescription.getDescription(), prescription.getVet().getName());
    }

    /**
     * 처방전 수정
     */
    @PutMapping("/api/v1/prescription/{id}")
    public UpdatePrescriptionResponse updatePrescriptionV1(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePrescriptionRequest request) {
        Long prescriptionId = prescriptionService.update(id, request.getDescription());
        Prescription prescription = prescriptionService.findOne(prescriptionId);

        return new UpdatePrescriptionResponse(prescription.getId(), prescription.getDescription());
    }

    /**
     * 처방전 생성 request, response
     */
    @Data
    @AllArgsConstructor
    static class CreatePrescriptionResponse {
        private Long id;
        private String description;
        private String doctor;
    }

    @Data
    static class CreatePrescriptionRequest {
        private String description;
        private Long vetId;
        private Long chartId;
    }

    /**
     * 처방전 수정 request, response
     */
    @Data
    @AllArgsConstructor
    static class UpdatePrescriptionResponse {
        private Long id;
        private String description;
    }

    @Data
    static class UpdatePrescriptionRequest {
        private String description;
    }
}

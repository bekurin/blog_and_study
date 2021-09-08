package street.pet.api.dto;

import street.pet.domain.Chart;
import street.pet.domain.ChartStatus;

import java.util.List;
import java.util.stream.Collectors;

public class ChartResponseDto {

    private Long id;
    private ChartStatus status;
    private VetResponseDto vet;
    private PetResponseDto pet;
    private List<PrescriptionResponseDto> prescriptions;

    public ChartResponseDto(Chart entity) {
        id = entity.getId();
        status = entity.getStatus();
        vet = new VetResponseDto(entity.getVet());
        pet = new PetResponseDto(entity.getPet());
        prescriptions = entity.getPrescriptions().stream()
                .map(prescription -> new PrescriptionResponseDto(prescription))
                .collect(Collectors.toList());
    }
}

package street.pet.web.dto;

import lombok.Data;
import street.pet.domain.Chart;
import street.pet.domain.ChartStatus;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ChartResponseDto {

    private Long id;
    private ChartStatus status;
    private String specialist;
    private String patient;
    private List<PrescriptionResponseDto> prescriptions;

    public ChartResponseDto(Chart entity) {
        id = entity.getId();
        specialist = entity.getVet().getName();
        patient = entity.getPet().getName();
        status = entity.getStatus();
        prescriptions = entity.getPrescriptions().stream()
                .map(prescription -> new PrescriptionResponseDto(prescription))
                .collect(Collectors.toList());
    }
}

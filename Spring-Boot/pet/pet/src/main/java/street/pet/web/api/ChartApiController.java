package street.pet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Chart;
import street.pet.repository.ChartRepository;
import street.pet.web.dto.ChartResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChartApiController extends BaseApiController{

    private final ChartRepository chartRepository;

    @GetMapping("/api/v1/charts")
    public Result chartsV1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<Chart> charts = chartRepository.findAllWithPetVet(offset, limit);
        List<ChartResponseDto> result = charts.stream()
                .map(chart -> new ChartResponseDto(chart))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/chart")
    public Result chartV1(
            @RequestParam(name = "id", defaultValue = "0") Long id) {
        List<Chart> charts = chartRepository.findByIdWithPetVet(id);
        List<ChartResponseDto> result = charts.stream()
                .map(chart -> new ChartResponseDto(chart))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }
}

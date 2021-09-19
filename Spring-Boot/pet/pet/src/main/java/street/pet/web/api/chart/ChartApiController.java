package street.pet.web.api.chart;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import street.pet.domain.Chart;
import street.pet.domain.ChartStatus;
import street.pet.repository.ChartRepository;
import street.pet.repository.ChartSearch;
import street.pet.service.ChartService;
import street.pet.web.api.BaseApiController;
import street.pet.web.api.chart.request.CreateChartRequest;
import street.pet.web.api.chart.request.UpdateChartRequest;
import street.pet.web.api.chart.response.CreateChartResponse;
import street.pet.web.api.chart.response.UpdateChartResponse;
import street.pet.web.dto.ChartResponseDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ChartApiController extends BaseApiController {

    private final ChartRepository chartRepository;
    private final ChartService chartService;

    /**
     * 차트 조회
     */
    @GetMapping("/api/v1/charts")
    public Result chartsV1(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit) throws NotFoundException {
        List<Chart> charts = chartRepository.findAllWithPetVet(offset, limit);
        List<ChartResponseDto> result = charts.stream()
                .map(chart -> new ChartResponseDto(chart))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/chart/{id}")
    public Result chartV1(
            @PathVariable Long id) throws NotFoundException {
        List<Chart> charts = chartRepository.findByIdWithPetVet(id);
        List<ChartResponseDto> result = charts.stream()
                .map(chart -> new ChartResponseDto(chart))
                .collect(Collectors.toList());
        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/chart/pet/{id}")
    public Result chartPetV1(
            @PathVariable Long id) throws NotFoundException {
        List<Chart> charts = chartRepository.findByPetWithPetVet(id);
        List<ChartResponseDto> result = charts.stream()
                .map(chart -> new ChartResponseDto(chart))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/chart/vet/{id}")
    public Result chartVetV1(
            @PathVariable Long id) throws NotFoundException {
        List<Chart> charts = chartRepository.findByVetWithPetVet(id);
        List<ChartResponseDto> result = charts.stream()
                .map(chart -> new ChartResponseDto(chart))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }
    
    @GetMapping("/api/v1/chart")
    public Result chartSearchV1(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "status") String status) throws NotFoundException {
        List<Chart> charts = chartRepository.findAllWithSearch(new ChartSearch(ChartStatus.valueOf(status), name));
        List<ChartResponseDto> result = charts.stream()
                .map(chart -> new ChartResponseDto(chart))
                .collect(Collectors.toList());
        
        return new Result(result.size(), result);
    }

    /**
     * 차트 생성
     */
    @PostMapping("/api/v1/chart")
    public CreateChartResponse createChartV1(
            @RequestBody @Valid CreateChartRequest request) {
        Long id = chartService.Chart(request.getPetId(), request.getVetId());
        Chart chart = chartService.findOne(id);

        return new CreateChartResponse(chart.getId(), chart.getVet().getName(), chart.getPet().getName());
    }

    /**
     * 차트 수정
     */
    @PutMapping("/api/v1/chart/{id}")
    public UpdateChartResponse updateChartV1(
            @PathVariable Long id,
            @RequestBody @Valid UpdateChartRequest request) {
        Long chartId = chartService.updateChart(id, request.getStatus());
        Chart chart = chartService.findOne(chartId);

        return new UpdateChartResponse(chart.getId(), chart.getStatus());
    }
}

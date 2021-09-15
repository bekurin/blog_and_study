package street.pet.web.api.chart.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import street.pet.domain.ChartStatus;

@Data
@AllArgsConstructor
public class UpdateChartResponse {
    private Long id;
    private ChartStatus status;
}

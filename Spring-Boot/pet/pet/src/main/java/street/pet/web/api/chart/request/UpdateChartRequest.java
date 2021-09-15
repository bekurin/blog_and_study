package street.pet.web.api.chart.request;

import lombok.Data;
import street.pet.domain.ChartStatus;

@Data
public class UpdateChartRequest {
    private ChartStatus status;
}

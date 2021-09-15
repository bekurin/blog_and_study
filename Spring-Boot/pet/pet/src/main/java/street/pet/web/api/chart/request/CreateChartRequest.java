package street.pet.web.api.chart.request;

import lombok.Data;

@Data
public class CreateChartRequest {
    private Long vetId;
    private Long petId;
}

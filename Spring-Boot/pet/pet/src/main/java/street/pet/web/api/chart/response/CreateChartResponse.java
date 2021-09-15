package street.pet.web.api.chart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateChartResponse {
    private Long id;
    private String vetName;
    private String petName;
}

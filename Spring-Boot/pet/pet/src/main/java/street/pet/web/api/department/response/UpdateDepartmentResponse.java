package street.pet.web.api.department.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateDepartmentResponse {
    private Long id;
    private String name;
}

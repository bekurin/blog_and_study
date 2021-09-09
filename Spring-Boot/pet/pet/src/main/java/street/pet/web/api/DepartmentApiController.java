package street.pet.web.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Department;
import street.pet.repository.DepartmentRepository;
import street.pet.web.dto.DepartmentResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DepartmentApiController extends BaseApiController {

    private final DepartmentRepository departmentRepository;

    @GetMapping("/api/v1/departments")
    public Result departmentsV1() {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDto> result = departments.stream()
                .map(department -> new DepartmentResponseDto(department))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/department")
    public DepartmentResponseDto departmentV1(
            @RequestParam(name = "id", defaultValue = "0") Long id) {
        Department department = departmentRepository.findOne(id);
        return new DepartmentResponseDto(department);
    }
}

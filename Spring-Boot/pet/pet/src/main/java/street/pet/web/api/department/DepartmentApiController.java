package street.pet.web.api.department;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import street.pet.domain.Department;
import street.pet.repository.DepartmentRepository;
import street.pet.service.DepartmentService;
import street.pet.web.api.BaseApiController;
import street.pet.web.api.department.request.CreateDepartmentRequest;
import street.pet.web.api.department.request.UpdateDepartmentRequest;
import street.pet.web.api.department.response.CreateDepartmentResponse;
import street.pet.web.api.department.response.UpdateDepartmentResponse;
import street.pet.web.dto.DepartmentResponseDto;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DepartmentApiController extends BaseApiController {

    private final DepartmentRepository departmentRepository;
    private final DepartmentService departmentService;

    /**
     * 진료과목 조회
     */
    @GetMapping("/api/v1/departments")
    public Result departmentsV1() throws NotFoundException {
        List<Department> departments = departmentRepository.findAll();
        List<DepartmentResponseDto> result = departments.stream()
                .map(department -> new DepartmentResponseDto(department))
                .collect(Collectors.toList());

        return new Result(result.size(), result);
    }

    @GetMapping("/api/v1/department/{id}")
    public DepartmentResponseDto departmentV1(
            @PathVariable Long id) {
        Department department = departmentRepository.findOne(id);
        return new DepartmentResponseDto(department);
    }

    /**
     * 진료과목 생성
     */
    @PostMapping("/api/v1/department")
    public CreateDepartmentResponse createDepartmentV1(
            @RequestBody @Valid CreateDepartmentRequest request) {
        Department department = Department.createDepartment(request.getName());
        Long id = departmentService.save(department);

        return new CreateDepartmentResponse(id, department.getName());
    }

    /**
     * 진료과목 수정
     */
    @PutMapping("/api/v1/department/{id}")
    public UpdateDepartmentResponse updateDepartmentV1(
            @PathVariable Long id,
            @RequestBody @Valid UpdateDepartmentRequest request) {
        Long departmentId = departmentService.update(id, request.getName());
        Department department = departmentService.findOne(departmentId);

        return new UpdateDepartmentResponse(department.getId(), department.getName());
    }

}

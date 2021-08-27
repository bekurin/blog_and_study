package street.pet.api;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import street.pet.domain.Address;
import street.pet.domain.Owner;
import street.pet.service.OwnerService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OwnerApiController {

    private final OwnerService ownerService;

    @GetMapping("api/v1/owners")
    public Result ownerV1(){
        List<Owner> findOwners = ownerService.findOwners();
        List<OwnerDto> collect = findOwners.stream()
                .map(o -> new OwnerDto(o.getName(), o.getAddress()))
                .collect(Collectors.toList());

        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class OwnerDto {
        private String name;
        private Address address;
    }
}

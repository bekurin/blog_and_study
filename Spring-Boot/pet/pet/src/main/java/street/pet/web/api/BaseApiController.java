package street.pet.web.api;

import lombok.AllArgsConstructor;
import lombok.Data;

public class BaseApiController {
    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}

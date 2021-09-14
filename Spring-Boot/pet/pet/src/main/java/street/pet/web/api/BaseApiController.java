package street.pet.web.api;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;

public class BaseApiController {
    @Data
    protected class Result<T>{
        private int count;
        private T data;

        public Result(int count, T data) throws NotFoundException {
            if(count == 0) {
                throw new NotFoundException("조회 내역이 존재하지 않습니다.");
            }
            this.count = count;
            this.data = data;
        }

    }
}

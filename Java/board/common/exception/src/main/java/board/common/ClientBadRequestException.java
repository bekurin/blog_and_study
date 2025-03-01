package board.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientBadRequestException extends ServerException {
    public ClientBadRequestException(String message) {
        super(message);
    }

    public ClientBadRequestException() {
        super(HttpStatus.BAD_REQUEST.getReasonPhrase());
    }
}

package net.bddtrader.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
public class MissingMandatoryFieldsException extends RuntimeException {
    public MissingMandatoryFieldsException(String message) {
        super(message);
    }
}

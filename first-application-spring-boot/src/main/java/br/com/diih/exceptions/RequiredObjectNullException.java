package br.com.diih.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectNullException extends RuntimeException {

    public RequiredObjectNullException() {
        super("It is not allowed to persist a null object!");
    }

    public RequiredObjectNullException(String message) {
        super(message);
    }
}

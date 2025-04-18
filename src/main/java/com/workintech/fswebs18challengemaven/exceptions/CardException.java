package com.workintech.fswebs18challengemaven.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CardException extends RuntimeException{

    private final String errorMessage;
    private final HttpStatus httpStatus;

    public CardException(String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }

}

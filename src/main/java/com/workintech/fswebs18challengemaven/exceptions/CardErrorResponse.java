package com.workintech.fswebs18challengemaven.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardErrorResponse {

    private String message;
    private String errorType = "UNKNOWN_ERROR"; ;
    private long timestamp;

    public CardErrorResponse(String message) {
        this.message = message;
    }
}

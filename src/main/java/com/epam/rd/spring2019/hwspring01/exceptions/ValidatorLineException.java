package com.epam.rd.spring2019.hwspring01.exceptions;

public class ValidatorLineException extends  ApplicationException{

    public ValidatorLineException(String message) {
        super(message);
    }

    public ValidatorLineException(String message, Throwable cause) {
        super(message, cause);
    }
}

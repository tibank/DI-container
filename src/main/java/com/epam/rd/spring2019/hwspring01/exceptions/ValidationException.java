package com.epam.rd.spring2019.hwspring01.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationException extends ApplicationException {

    private List<String> errList = new ArrayList<>();

    public List<String> getErrList() {
        return errList;
    }

    public ValidationException(String message) {
        super(message);
    }
}

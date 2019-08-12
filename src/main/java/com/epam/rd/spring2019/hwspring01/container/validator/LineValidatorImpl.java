package com.epam.rd.spring2019.hwspring01.container.validator;

import com.epam.rd.spring2019.hwspring01.exceptions.ValidatorLineException;

public class LineValidatorImpl implements LineValidator {

    @Override
    public void validateLine(String str) {
        if (!(isLineContainsBean(str)
                ||  (isLineContainsPropertyValue(str) && !isLineContainsPropertyReference(str))
                ||  (!isLineContainsPropertyValue(str) && isLineContainsPropertyReference(str)))) {
            throw  new ValidatorLineException("Wrong format line: " + str);
        }
    }

    private boolean isLineContainsBean(String line) {
        return line.startsWith("[") && line.endsWith("]") &&
                line.indexOf(":") > 1 && line.indexOf(":") < line.length() - 1;
    }

    private boolean isLineContainsPropertyValue(String line) {
        return line.indexOf("=val:") > 0 && line.indexOf("=val:") == line.lastIndexOf("=val:");
    }

    private boolean isLineContainsPropertyReference(String line) {
        return line.indexOf("=ref:") > 0 && line.indexOf("=ref:") == line.lastIndexOf("=ref:");
    }
}

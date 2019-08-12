package com.epam.rd.spring2019.hwspring01.container.validator;

import com.epam.rd.spring2019.hwspring01.container.loader.LoaderFromConfigFile;
import com.epam.rd.spring2019.hwspring01.exceptions.ValidationConfigException;
import com.epam.rd.spring2019.hwspring01.exceptions.ValidatorLineException;

import java.util.List;

public class ConfigValidatorImpl implements ConfigValidator {
    private final LineValidator lineValidator;

    public ConfigValidatorImpl(LineValidator lineValidator) {
        this.lineValidator = lineValidator;
    }

    @Override
    public void validateConfig(LoaderFromConfigFile loader) {
        List<String> linesConfig = loader.getLinesConfig();
        if (linesConfig.size() < 2) {
            throw new ValidationConfigException("File-ini has to contained more than one line!");
        }

        if (!linesConfig.get(0).startsWith("[")) {
            throw new ValidationConfigException("First line in file ini is not a bean difinition");
        }

        for (int i = 0; i < linesConfig.size(); i++) {
            try {
                lineValidator.validateLine(linesConfig.get(i));
            } catch (ValidatorLineException ex) {
                throw new ValidatorLineException(ex.getMessage() + " line # " + (i + 1), ex);
            }
        }
    }
}

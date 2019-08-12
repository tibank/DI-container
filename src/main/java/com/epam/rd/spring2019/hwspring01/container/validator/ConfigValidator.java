package com.epam.rd.spring2019.hwspring01.container.validator;

import com.epam.rd.spring2019.hwspring01.container.loader.LoaderFromConfigFile;

public interface ConfigValidator {
    void validateConfig(LoaderFromConfigFile loader);
}

package com.epam.rd.spring2019.hwspring01.container.loader;

import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.models.BeanProperty;
import com.epam.rd.spring2019.hwspring01.container.models.TypeProperty;
import com.epam.rd.spring2019.hwspring01.container.sorter.GraphBeansSorter;
import com.epam.rd.spring2019.hwspring01.container.validator.ConfigValidator;
import com.epam.rd.spring2019.hwspring01.container.validator.GraphBeansValidator;
import com.epam.rd.spring2019.hwspring01.exceptions.ApplicationException;
import com.epam.rd.spring2019.hwspring01.exceptions.ParserFileINIException;
import lombok.Builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
public class ContainerConfigLoaderINIImpl implements ContainerConfigLoader {
    private static final String delimiterValue = "=val:";
    private static final String delimiterReference = "=ref:";

    private final LoaderFromConfigFile loaderConfigFile;
    private final ConfigValidator configValidator;
    private final GraphBeansValidator graphValidator;
    private final GraphBeansSorter graphSorter;

    @Override
    public List<Bean> loadConfig() throws IOException, ClassNotFoundException {
        loaderConfigFile.load();
        try {
            configValidator.validateConfig(loaderConfigFile);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage(), ex);
        }

        List<Bean> beans = getBeansFromConfig();
        try {
            graphValidator.validateGraphBeans(beans);
        } catch (ApplicationException ex) {
            throw new ApplicationException(ex.getMessage(), ex);
        }
        graphSorter.sortBeans(beans);

        return beans;
    }

    private List<Bean> getBeansFromConfig() throws ClassNotFoundException {
        List<String> linesConfig = loaderConfigFile.getLinesConfig();
        List<Bean> result = new ArrayList<>();
        Bean bean = null;

        boolean isBeanAlreadyExists = false;
        String s;
        for (String aLinesConfig : linesConfig) {
            s = aLinesConfig;
            if (s.startsWith("[")) {
                if (isBeanAlreadyExists) {
                    result.add(bean);
                } else {
                    isBeanAlreadyExists = true;
                }
                bean = getBeanFromLine(s);
            } else if (s.indexOf(delimiterValue) > 0) {
                Objects.requireNonNull(bean).getPropertiesBean().put(getNameProperty(s, delimiterValue),
                        new BeanProperty(TypeProperty.VAL, getValueProperty(s, delimiterValue)));
            } else if (s.indexOf(delimiterReference) > 0) {
                Objects.requireNonNull(bean).getPropertiesBean().put(getNameProperty(s, delimiterReference),
                        new BeanProperty(TypeProperty.REF, getValueProperty(s, delimiterReference)));
            }
        }
        result.add(bean);

        return result;
    }

    private Bean getBeanFromLine(String line) throws ClassNotFoundException {
        return new Bean(getBeanId(line), getBeanClassNameClass(line), -1);
    }

    private String getBeanId(String line) {
        return line.substring(line.indexOf(":") + 1, line.length() - 1);
    }

    private Class getBeanClassNameClass(String line) throws ClassNotFoundException {
        return Class.forName(getBeanClassNameString(line));
    }

    private String getBeanClassNameString(String line) {
        return line.substring(1, line.indexOf(":"));
    }

    private String getNameProperty(String line, String delimiter) {
        int posValue = line.indexOf(delimiter);
        if (posValue > 0) {
            return line.substring(0, posValue);
        } else {
            throw new ParserFileINIException("Line " + line + " doesn't contain delimiter for reference property '=ref:'");
        }
    }

    private String getValueProperty(String line, String delimiter) {
        int posValue = line.indexOf(delimiter);
        if (posValue > 0) {
            return line.substring(posValue + delimiter.length());
        } else {
            throw new ParserFileINIException("Line " + line + " doesn't contain delimiter for reference property '=ref:'");
        }
    }
}

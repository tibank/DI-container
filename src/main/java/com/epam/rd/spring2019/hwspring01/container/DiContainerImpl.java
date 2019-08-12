package com.epam.rd.spring2019.hwspring01.container;

import com.epam.rd.spring2019.hwspring01.container.converter.ConvertStringPrimitiveImpl;
import com.epam.rd.spring2019.hwspring01.container.creator.CreatorBeans;
import com.epam.rd.spring2019.hwspring01.container.creator.CreatorBeansImpl;
import com.epam.rd.spring2019.hwspring01.container.loader.ContainerConfigLoader;
import com.epam.rd.spring2019.hwspring01.container.loader.ContainerConfigLoaderINIImpl;
import com.epam.rd.spring2019.hwspring01.container.loader.LoaderFromConfigFileImpl;
import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.sorter.GraphBeansSorterImpl;
import com.epam.rd.spring2019.hwspring01.container.validator.ConfigValidatorImpl;
import com.epam.rd.spring2019.hwspring01.container.validator.GraphBeansValidatorImpl;
import com.epam.rd.spring2019.hwspring01.container.validator.LineValidatorImpl;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Slf4j
public class DiContainerImpl implements DIContainer {
    private final CreatorBeans beansCreator;

    public DiContainerImpl(String configFileName) throws ClassNotFoundException, IOException {

        ContainerConfigLoader containerConfigLoader = ContainerConfigLoaderINIImpl
                .builder()
                .loaderConfigFile(new LoaderFromConfigFileImpl(configFileName))
                .configValidator(new ConfigValidatorImpl(new LineValidatorImpl()))
                .graphValidator(new GraphBeansValidatorImpl())
                .graphSorter(new GraphBeansSorterImpl())
                .build();
        List<Bean> listBeans = containerConfigLoader.loadConfig();

        this.beansCreator = new CreatorBeansImpl(new ConvertStringPrimitiveImpl(),
                Collections.unmodifiableList(listBeans));
    }

    @Override
    public Object getInstance(Class classBean) {
        return beansCreator.createObjectFromBean(classBean);
    }
}

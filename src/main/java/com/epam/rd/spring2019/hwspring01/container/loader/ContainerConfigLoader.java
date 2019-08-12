package com.epam.rd.spring2019.hwspring01.container.loader;

import com.epam.rd.spring2019.hwspring01.container.models.Bean;

import java.io.IOException;
import java.util.List;

public interface ContainerConfigLoader {
    List<Bean> loadConfig() throws IOException, ClassNotFoundException;
}

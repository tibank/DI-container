package com.epam.rd.spring2019.hwspring01.container.loader;

import java.io.IOException;
import java.util.List;

public interface LoaderFromConfigFile {
    void load() throws IOException;
    List<String> getLinesConfig();
}

package com.epam.rd.spring2019.hwspring01.container.loader;

import com.epam.rd.spring2019.hwspring01.exceptions.ApplicationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoaderFromConfigFileImpl implements LoaderFromConfigFile {
    private final String configFileName;
    private List<String> linesConfig;

    public LoaderFromConfigFileImpl(String configFileName) {
        this.configFileName = configFileName;
    }

    @Override
    public void load() throws IOException {
        List<String> lines = new ArrayList<>();

        URL url = getClass().getResource("/" + configFileName);
        if (url == null) {
            throw new ApplicationException("Doesn't exist ini-file " + configFileName);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getFile()))) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                s = s.trim();
                if (s.isEmpty()) {
                    continue;
                }
                lines.add(s);
            }
        }

        linesConfig = Collections.unmodifiableList(lines);
    }

    @Override
    public List<String> getLinesConfig() {
        return linesConfig;
    }
}

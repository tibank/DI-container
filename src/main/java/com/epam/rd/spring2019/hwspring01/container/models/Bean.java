package com.epam.rd.spring2019.hwspring01.container.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
public class Bean {
    public final String id;
    public final Class classBean;
    @Getter @Setter Integer levelDI;
    @Getter Map<String,BeanProperty> propertiesBean = new HashMap<>();

    public Bean(String id, Class classBean, Integer levelDI) {
        this.id = id;
        this.classBean = classBean;
        this.levelDI = levelDI;
    }
}

package com.epam.rd.spring2019.hwspring01.container.models;

public class BeanProperty {
    public final TypeProperty typeProperty;
    public final String value;

    public BeanProperty(TypeProperty typeProperty, String value) {
        this.typeProperty = typeProperty;
        this.value = value;
    }
}

package com.epam.rd.spring2019.hwspring01.container.converter;

public interface ConvertStringPrimitive<T> {

    T convertStringToPrimitiveWrapper(Class t, String str);
}

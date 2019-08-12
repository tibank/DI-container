package com.epam.rd.spring2019.hwspring01.container.converter;

public class ConvertStringPrimitiveImpl<T> implements ConvertStringPrimitive {

    @Override
    public T convertStringToPrimitiveWrapper(Class t, String value) {
        if (t == String.class) {
            return (T) String.valueOf(value);
        } else if (t == Long.class) {
            return (T) Long.valueOf(value);
        } else if (t == Integer.class) {
            return (T) Integer.valueOf(value);
        } else if (t == Short.class) {
            return (T) Short.valueOf(value);
        } else if (t == Byte.class) {
            return (T) Byte.valueOf(value);
        } else if (t == Double.class) {
            return (T) Double.valueOf(value);
        } else if (t == Float.class) {
            return (T) Float.valueOf(value);
        } else if (t == Boolean.class) {
            return (T) Boolean.valueOf(value);
        } else {
            throw new ClassCastException("Can't convert '" + value + "' to " + t.getSimpleName());
        }
    }
}

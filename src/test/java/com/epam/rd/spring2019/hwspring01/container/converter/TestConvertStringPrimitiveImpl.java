package com.epam.rd.spring2019.hwspring01.container.converter;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestConvertStringPrimitiveImpl {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testConvertString() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(String.class, "35"),
                instanceOf(String.class));
    }

    @Test
    public void testConvertStringToLong() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(Long.class, "35"),
                instanceOf(Long.class));
    }

    @Test
    public void testConvertStringToInteger() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(Integer.class, "35"),
                instanceOf(Integer.class));
    }

    @Test
    public void testConvertStringToShort() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(Short.class, "35"),
                instanceOf(Short.class));
    }

    @Test
    public void testConvertStringToByte() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(Byte.class, "35"),
                instanceOf(Byte.class));
    }

    @Test
    public void testConvertStringToDouble() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(Double.class, "35.5"),
                instanceOf(Double.class));
    }

    @Test
    public void testConvertStringToFloat() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(Float.class, "35.5"),
                instanceOf(Float.class));
    }

    @Test
    public void testConvertStringToBoolean() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();
        //THEN
        assertThat(convertStringPrimitive.convertStringToPrimitiveWrapper(Boolean.class, "true"),
                instanceOf(Boolean.class));
    }

    @Test
    public void testConvertStringToPrimitiveWrapperException() {
        //GIVEN
        ConvertStringPrimitive convertStringPrimitive = new ConvertStringPrimitiveImpl();

        //GIVEN
        thrown.expect(ClassCastException.class);
        thrown.expectMessage(CoreMatchers.containsString("Can't convert"));

        //WHEN
        convertStringPrimitive.convertStringToPrimitiveWrapper(int.class, "35");

    }
}

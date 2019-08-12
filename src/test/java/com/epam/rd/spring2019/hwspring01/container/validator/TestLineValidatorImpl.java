package com.epam.rd.spring2019.hwspring01.container.validator;

import com.epam.rd.spring2019.hwspring01.exceptions.ValidatorLineException;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

public class TestLineValidatorImpl {

    private final LineValidator lineValidator = new LineValidatorImpl();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testLineContainsBean() {
        //GIVEN
        String testLine = "[com.epam.rd.spring2019.hwspring01.models.Country:countryUkraine]";

        //WHEN
        try {
            lineValidator.validateLine(testLine);
        } catch (ValidatorLineException e) {
            fail("Should not have thrown ValidatorLineException");
        }
    }

    @Test
    public void testLineNotContainsBeanNotContainsProperty() {
        //GIVEN
        String testLine = "com.epam.rd.spring2019.hwspring01.models.Country:countryUkraine]";

        thrown.expect(ValidatorLineException.class);
        thrown.expectMessage(CoreMatchers.containsString("Wrong format line"));

        //WHEN
        lineValidator.validateLine(testLine);
    }

    @Test
    public void testLineNotContainsBeanContainsValueProperty() {
        //GIVEN
        String testLine = "street=val:Volodymyr Monomakh";

        //WHEN
        try {
            lineValidator.validateLine(testLine);
        } catch (ValidatorLineException e) {
            fail("Should not have thrown ValidatorLineException");
        }
    }

    @Test
    public void testLineNotContainsBeanContainsReferenceProperty() {
        //GIVEN
        String testLine = "city=ref:cityDnipro";

        //WHEN
        try {
            lineValidator.validateLine(testLine);
        } catch (ValidatorLineException e) {
            fail("Should not have thrown ValidatorLineException");
        }
    }

    @Test
    public void testLineNotContainsBeanContainsBothTypeProperty() {
        //GIVEN
        String testLine = "street=val:Volodymyr Monomakh city=ref:cityDnipro";

        thrown.expect(ValidatorLineException.class);
        thrown.expectMessage(CoreMatchers.containsString("Wrong format line"));

        //WHEN
        lineValidator.validateLine(testLine);
    }
}

package com.epam.rd.spring2019.hwspring01.container.validator;

import com.epam.rd.spring2019.hwspring01.container.loader.LoaderFromConfigFile;
import com.epam.rd.spring2019.hwspring01.container.loader.LoaderFromConfigFileImpl;
import com.epam.rd.spring2019.hwspring01.exceptions.ValidationConfigException;
import com.epam.rd.spring2019.hwspring01.exceptions.ValidatorLineException;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestConfigValidatorImpl {

    private final LineValidator lineValidator = new LineValidatorImpl();
    private final ConfigValidator configValidator = new ConfigValidatorImpl(lineValidator);

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testValidateConfigExceptionCountOfLines() {
        //GIVEN
        String[] testLinesFromConfig = new String[] {"[com.epam.rd.spring2019.hwspring01.models.Address:addressDnipro]"};
        List<String> linesConfig = new ArrayList<>(Arrays.asList(testLinesFromConfig));

        //WHEN
        LoaderFromConfigFile mockLoader = mock(LoaderFromConfigFileImpl.class);
        when(mockLoader.getLinesConfig()).thenReturn(linesConfig);

        thrown.expect(ValidationConfigException.class);
        thrown.expectMessage(CoreMatchers.containsString("File-ini has to contained more than one line"));

        //THEN
        configValidator.validateConfig(mockLoader);
    }

    @Test
    public void testValidateConfigExceptionNoCorrectFirstLine() {
        //GIVEN
        String[] testLinesFromConfig = new String[]
                {"com.epam.rd.spring2019.hwspring01.models.Address:addressDnipro]",
                "id=val:5",
                "street=val:Volodymyr Monomakh"};
        List<String> linesConfig = new ArrayList<>(Arrays.asList(testLinesFromConfig));

        //WHEN
        LoaderFromConfigFile mockLoader = mock(LoaderFromConfigFileImpl.class);
        when(mockLoader.getLinesConfig()).thenReturn(linesConfig);

        thrown.expect(ValidationConfigException.class);
        thrown.expectMessage(CoreMatchers.containsString("First line in file ini is not a bean difinition"));

        //THEN
        configValidator.validateConfig(mockLoader);
    }

    @Test
    public void testValidateAllLinesExceptionErrorLine() {
        //GIVEN
        String[] testLinesFromConfig = new String[]
                {"[com.epam.rd.spring2019.hwspring01.models.Address:addressDnipro]",
                        "id=val;5",
                        "street=val:Volodymyr Monomakh"};
        List<String> linesConfig = new ArrayList<>(Arrays.asList(testLinesFromConfig));

        //WHEN
        LoaderFromConfigFile mockLoader = mock(LoaderFromConfigFileImpl.class);
        when(mockLoader.getLinesConfig()).thenReturn(linesConfig);

        thrown.expect(ValidatorLineException.class);
        thrown.expectMessage(CoreMatchers.containsString("line # 2"));

        //THEN
        configValidator.validateConfig(mockLoader);
    }

    @Test
    public void testValidateConfigNoException() {
        //GIVEN
        String[] testLinesFromConfig = new String[]
                {"[com.epam.rd.spring2019.hwspring01.models.Address:addressDnipro]",
                        "id=val:5",
                        "street=val:Volodymyr Monomakh"};
        List<String> linesConfig = new ArrayList<>(Arrays.asList(testLinesFromConfig));

        //WHEN
        LoaderFromConfigFile mockLoader = mock(LoaderFromConfigFileImpl.class);
        when(mockLoader.getLinesConfig()).thenReturn(linesConfig);

        try {
            configValidator.validateConfig(mockLoader);
        } catch (ValidationConfigException|ValidatorLineException e) {
            fail("Should not have thrown any exception");
        }
    }
}

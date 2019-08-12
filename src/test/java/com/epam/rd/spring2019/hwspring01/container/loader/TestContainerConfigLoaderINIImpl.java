package com.epam.rd.spring2019.hwspring01.container.loader;

import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.sorter.GraphBeansSorter;
import com.epam.rd.spring2019.hwspring01.container.sorter.GraphBeansSorterImpl;
import com.epam.rd.spring2019.hwspring01.container.validator.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestContainerConfigLoaderINIImpl {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final LineValidator lineValidator = new LineValidatorImpl();
    private final ConfigValidator configValidator = new ConfigValidatorImpl(lineValidator);
    private final GraphBeansValidator graphValidator = new GraphBeansValidatorImpl();
    private final GraphBeansSorter graphSorter = new GraphBeansSorterImpl();

    private String[] testLinesFromConfig = new String[]{
            "[com.epam.rd.spring2019.hwspring01.models.Address:addressDnipro]",
            "id=val:5",
            "street=val:Volodymyr Monomakh",
            "numberHouse=val:17A",
            "city=ref:cityDnipro",
            "[com.epam.rd.spring2019.hwspring01.models.User:userAdmin]",
            "id=val:1",
            "name=val:admin",
            "email=val:admin@admin.com",
            "password=val:1q2w3e4r",
            "address=ref:addressDnipro",
            "[com.epam.rd.spring2019.hwspring01.models.Country:countryUkraine]",
            "id=val:1",
            "name=val:Ukraine",
            "[com.epam.rd.spring2019.hwspring01.models.City:cityDnipro]",
            "id=val:1",
            "name=val:Dnipro",
            "country=ref:countryUkraine",
            "[com.epam.rd.spring2019.hwspring01.services.UserServiceImpl:userService]",
            "userDao=ref:userDao",
            "securityService=ref:userSecurity",
            "userValidator=ref:userValidator",
            "[com.epam.rd.spring2019.hwspring01.daos.UserDaoImpl:userDao]",
            "[com.epam.rd.spring2019.hwspring01.validators.UserValidatorImpl:userValidator]",
            "[com.epam.rd.spring2019.hwspring01.services.SecurityServiceImpl:userSecurity]"
    };

    @Mock
    private LoaderFromConfigFileImpl mockLoader;

    @InjectMocks
    private ContainerConfigLoaderINIImpl containerConfigLoader;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        containerConfigLoader = ContainerConfigLoaderINIImpl
                .builder()
                .loaderConfigFile(mockLoader)
                .configValidator(new ConfigValidatorImpl(new LineValidatorImpl()))
                .graphValidator(new GraphBeansValidatorImpl())
                .graphSorter(new GraphBeansSorterImpl())
                .build();
    }

    @Test
    public void testContainerConfigLoader() throws IOException, ClassNotFoundException {
        //GIVEN

        List<String> linesConfig = new ArrayList<>(Arrays.asList(testLinesFromConfig));

        // WHEN
        //LoaderFromConfigFile mockLoader = mock(LoaderFromConfigFileImpl.class);
        doNothing().when(mockLoader).load();
        when(mockLoader.getLinesConfig()).thenReturn(linesConfig);

        List<Bean> beans = containerConfigLoader.loadConfig();

        //THEN
        Assert.assertEquals(8, beans.size());
    }
}

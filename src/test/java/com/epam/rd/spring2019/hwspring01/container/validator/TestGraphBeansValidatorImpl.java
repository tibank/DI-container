package com.epam.rd.spring2019.hwspring01.container.validator;

import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.models.BeanProperty;
import com.epam.rd.spring2019.hwspring01.container.models.TypeProperty;
import com.epam.rd.spring2019.hwspring01.exceptions.BeanNotFoundException;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class TestGraphBeansValidatorImpl {

    private final GraphBeansValidator graphBeansValidator = new GraphBeansValidatorImpl();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGraphBeanValidator_NoException() throws ClassNotFoundException {
        //GIVEN
        List<Bean> beans = new ArrayList<>();
        Bean beanCountry = new Bean("countryUkraine",
                Class.forName("com.epam.rd.spring2019.hwspring01.models.Country"),0);
        beanCountry.getPropertiesBean().put("id", new BeanProperty(TypeProperty.VAL,"1"));
        beanCountry.getPropertiesBean().put("name", new BeanProperty(TypeProperty.VAL,"Ukraine"));
        beans.add(beanCountry);

        Bean beanCity = new Bean("cityDnipro",
                Class.forName("com.epam.rd.spring2019.hwspring01.models.City"),1);
        beanCity.getPropertiesBean().put("id", new BeanProperty(TypeProperty.VAL,"1"));
        beanCity.getPropertiesBean().put("name", new BeanProperty(TypeProperty.VAL,"Dnipro"));
        beanCity.getPropertiesBean().put("country", new BeanProperty(TypeProperty.REF,"countryUkraine"));
        beans.add(beanCity);

        //WHEN
        try {
            graphBeansValidator.validateGraphBeans(beans);
        } catch (BeanNotFoundException e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testGraphBeanValidatorException() throws ClassNotFoundException {
        //GIVEN
        List<Bean> beans = new ArrayList<>();
        Bean beanCountry = new Bean("countryUkraine",
                Class.forName("com.epam.rd.spring2019.hwspring01.models.Country"),0);
        beanCountry.getPropertiesBean().put("id", new BeanProperty(TypeProperty.VAL,"1"));
        beanCountry.getPropertiesBean().put("name", new BeanProperty(TypeProperty.VAL,"Ukraine"));
        beans.add(beanCountry);

        Bean beanCity = new Bean("cityDnipro",
                Class.forName("com.epam.rd.spring2019.hwspring01.models.City"),1);
        beanCity.getPropertiesBean().put("id", new BeanProperty(TypeProperty.VAL,"1"));
        beanCity.getPropertiesBean().put("name", new BeanProperty(TypeProperty.VAL,"Dnipro"));
        beanCity.getPropertiesBean().put("country", new BeanProperty(TypeProperty.REF,"countryUkr"));
        beans.add(beanCity);

        //WHEN
        thrown.expect(BeanNotFoundException.class);
        thrown.expectMessage(CoreMatchers.containsString("Not found bean by id: countryUkr"));

        graphBeansValidator.validateGraphBeans(beans);
    }
}

package com.epam.rd.spring2019.hwspring01.container.sorter;

import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.models.BeanProperty;
import com.epam.rd.spring2019.hwspring01.container.models.TypeProperty;
import com.epam.rd.spring2019.hwspring01.models.Address;
import com.epam.rd.spring2019.hwspring01.models.City;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

public class TestGraphBeansSorter {

    private final GraphBeansSorter graphBeansSorter = new GraphBeansSorterImpl();

    @Test
    public void testSortBeans() throws ClassNotFoundException {
        //GIVEN
        List<Bean> beans = new ArrayList<>();
        Bean beanCity = new Bean("cityDnipro",
                Class.forName("com.epam.rd.spring2019.hwspring01.models.City"),-1);
        beanCity.getPropertiesBean().put("id", new BeanProperty(TypeProperty.VAL,"1"));
        beanCity.getPropertiesBean().put("name", new BeanProperty(TypeProperty.VAL,"Dnipro"));
        beanCity.getPropertiesBean().put("country", new BeanProperty(TypeProperty.REF,"countryUkraine"));
        beans.add(beanCity);

        Bean beanAddress = new Bean("addressDnipro",
                Class.forName("com.epam.rd.spring2019.hwspring01.models.Address"),-1);
        beanAddress.getPropertiesBean().put("id", new BeanProperty(TypeProperty.VAL,"5"));
        beanAddress.getPropertiesBean().put("street", new BeanProperty(TypeProperty.VAL,"Volodymyr Monomakh"));
        beanAddress.getPropertiesBean().put("numberHouse", new BeanProperty(TypeProperty.VAL,"17A"));
        beanAddress.getPropertiesBean().put("city", new BeanProperty(TypeProperty.REF,"cityDnipro"));
        beans.add(beanAddress);

        Bean beanCountry = new Bean("countryUkraine",
                Class.forName("com.epam.rd.spring2019.hwspring01.models.Country"),-1);
        beanCountry.getPropertiesBean().put("id", new BeanProperty(TypeProperty.VAL,"1"));
        beanCountry.getPropertiesBean().put("name", new BeanProperty(TypeProperty.VAL,"Ukraine"));
        beans.add(beanCountry);

        //WHEN
        graphBeansSorter.sortBeans(beans);

        //THEN
        assertThat(beans.get(0).id, CoreMatchers.containsString("countryUkraine"));
        assertThat(beans.get(0).getLevelDI(), CoreMatchers.equalTo(0));
        assertThat(beans.get(0).classBean.getName(), CoreMatchers.containsString("Country"));

        assertThat(beans.get(1).id, CoreMatchers.containsString("cityDnipro"));
        assertThat(beans.get(1).getLevelDI(), CoreMatchers.equalTo(1));
        assertThat(beans.get(1).classBean, CoreMatchers.equalTo(City.class));

        assertThat(beans.get(2).id, CoreMatchers.containsString("addressDnipro"));
        assertThat(beans.get(2).getLevelDI(), CoreMatchers.equalTo(2));
        assertThat(beans.get(2).classBean, CoreMatchers.equalTo(Address.class));


    }
}

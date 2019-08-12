package com.epam.rd.spring2019.hwspring01.container.validator;

import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.models.BeanProperty;
import com.epam.rd.spring2019.hwspring01.container.models.TypeProperty;
import com.epam.rd.spring2019.hwspring01.exceptions.BeanNotFoundException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphBeansValidatorImpl implements GraphBeansValidator {

    @Override
    public void validateGraphBeans(List<Bean> beans) {
        Set<String> beansId = new HashSet<>();

        for (Bean bean: beans) {
            beansId.add(bean.id);
        }

        for (Bean bean: beans) {
            for (BeanProperty beanProperty: bean.getPropertiesBean().values()) {
                if (beanProperty.typeProperty == TypeProperty.REF && !beansId.contains(beanProperty.value)) {
                    throw new BeanNotFoundException("Not found bean by id: " + beanProperty.value);
                }
            }
        }
    }
}

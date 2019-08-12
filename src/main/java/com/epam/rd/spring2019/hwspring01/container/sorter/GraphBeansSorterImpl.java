package com.epam.rd.spring2019.hwspring01.container.sorter;

import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.models.BeanProperty;
import com.epam.rd.spring2019.hwspring01.container.models.TypeProperty;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphBeansSorterImpl implements GraphBeansSorter {

    @Override
    public void sortBeans(List<Bean> beans) {
        setLevelDI(beans);
        beans.sort(Comparator.comparingInt(Bean::getLevelDI));
    }

    private void setLevelDI(List<Bean> beans) {
        Map<String,Bean> assistanceMap = new HashMap<>();

        while (beans.size() > assistanceMap.size()) {
            for (Bean bean: beans) {
                if (assistanceMap.containsKey(bean.id)) {
                    continue;
                }
                if (countPropertyReference(bean) == 0) {
                    bean.setLevelDI(0);
                    assistanceMap.put(bean.id,bean);
                } else {
                    boolean isAllRefs = true;
                    for (BeanProperty prop: bean.getPropertiesBean().values()) {
                        if (prop.typeProperty == TypeProperty.REF) {
                            if (assistanceMap.containsKey(prop.value)) {
                                bean.setLevelDI(assistanceMap.get(prop.value).getLevelDI()+1);
                            } else {
                                isAllRefs = false;
                            }
                        }
                    }
                    if (isAllRefs) {
                        assistanceMap.put(bean.id,bean);
                    }
                }
            }
        }
    }

    private int countPropertyReference(Bean bean) {
        int result = 0;

        for (BeanProperty prop: bean.getPropertiesBean().values()) {
            if (prop.typeProperty == TypeProperty.REF) {
                result++;
            }
        }

        return result;
    }
}

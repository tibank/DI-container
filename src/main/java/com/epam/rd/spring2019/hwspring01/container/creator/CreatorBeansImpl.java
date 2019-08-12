package com.epam.rd.spring2019.hwspring01.container.creator;

import com.epam.rd.spring2019.hwspring01.container.converter.ConvertStringPrimitive;
import com.epam.rd.spring2019.hwspring01.container.models.Bean;
import com.epam.rd.spring2019.hwspring01.container.models.BeanProperty;
import com.epam.rd.spring2019.hwspring01.container.models.TypeProperty;
import com.epam.rd.spring2019.hwspring01.exceptions.ApplicationException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;

public class CreatorBeansImpl implements CreatorBeans {
    private final ConvertStringPrimitive convertToPrimitive;
    private final List<Bean> listBeans;
    private final Map<Class,Integer> mapIndexesBeans;
    private final Map<String,Integer> mapIndexesBeansId;

    public CreatorBeansImpl(ConvertStringPrimitive convertToPrimitive, List<Bean> listBeans) {
        this.convertToPrimitive = convertToPrimitive;
        this.listBeans = listBeans;
        mapIndexesBeans = new HashMap<>();
        mapIndexesBeansId = new HashMap<>();

        fillMapIndexes();
    }

    private void fillMapIndexes() {
        for (int i = 0; i < listBeans.size(); i++) {
            mapIndexesBeans.put(listBeans.get(i).classBean,i);
            mapIndexesBeansId.put(listBeans.get(i).id,i);
        }
    }

    @Override
    public Object createObjectFromBean(Class classBean) {
        return creatorBeanObject(listBeans.get(mapIndexesBeans.get(classBean)));
    }

    private Object creatorBeanObject(Bean bean)  {

        Constructor beanConstructor;
        try {
            beanConstructor = getBeanConstructur(bean);
        } catch (NoSuchMethodException ex) {
            throw new ApplicationException("There is no appropriate constructor for " +
                    bean.classBean.getName(),ex);
        }

        Object arglist[] = new Object[bean.getPropertiesBean().size()];

        Class classBean = bean.classBean;
        Map<Integer, Object> fieldsClass = new TreeMap<>();
        Field[] fields = classBean.getDeclaredFields();

        for (Map.Entry<String, BeanProperty> entry : bean.getPropertiesBean().entrySet()) {
            for (int i = 0; i < fields.length; i++) {
                if (!entry.getKey().equals(fields[i].getName())) {
                    continue;
                }
                Type fieldType = fields[i].getType();
                Class fieldClass = (Class) fieldType;
                String fieldValue = entry.getValue().value;

                if (entry.getValue().typeProperty == TypeProperty.VAL) {
                    if (fieldClass != Character.class && fieldClass != char.class) {
                        if (fieldClass.isPrimitive()) {
                            fieldClass = getClassWrapper(fieldClass);
                        }

                        fieldsClass.put(i, convertToPrimitive.convertStringToPrimitiveWrapper(fieldClass, fieldValue));
                    } else if (fieldClass == Character.class || fieldClass == char.class) {
                        if (fieldValue.length() == 1) {
                            fieldsClass.put(i, fieldValue.charAt(0));
                        } else {
                            throw new ClassCastException("Can't convert '" +
                                    fieldValue + "' to " + fieldClass.getSimpleName());
                        }
                    } else {
                        throw new ClassCastException("Can't convert '" +
                                fieldValue + "' to " + fieldClass.getSimpleName());
                    }
                } else {
                    Bean beanDI = listBeans.get(mapIndexesBeansId.get(fieldValue));
                    fieldsClass.put(i, creatorBeanObject(beanDI));
                }
            }
        }
        fieldsClass.values().toArray(arglist);

        try {
            return beanConstructor.newInstance(arglist);
        } catch (InstantiationException|IllegalAccessException|InvocationTargetException ex) {
            throw new ApplicationException("Error creating new bean for class " +
                    bean.classBean.getName(),ex);
        }
    }

    private Constructor getBeanConstructur(Bean bean) throws NoSuchMethodException {
        Class classBean = bean.classBean;
        Map<Integer, Class> fieldsClass = new TreeMap<>();
        Field[] fields = classBean.getDeclaredFields();
        Class[] paramTypes = new Class[bean.getPropertiesBean().size()];

        for (String nameBean : bean.getPropertiesBean().keySet()) {
            for (int i = 0; i < fields.length; i++) {
                if (nameBean.equals(fields[i].getName())) {
                    fieldsClass.put(i, fields[i].getType());
                }
            }
        }

        fieldsClass.values().toArray(paramTypes);
        return classBean.getDeclaredConstructor(paramTypes);
    }

    private Class getClassWrapper(Class t) {

        if (t == long.class) {
            return Long.class;
        } else if (t == int.class) {
            return Integer.class;
        } else if (t == short.class) {
            return Short.class;
        } else if (t == byte.class) {
            return Byte.class;
        } else if (t == double.class) {
            return Double.class;
        } else if (t == float.class) {
            return Float.class;
        } else if (t == char.class) {
            return Character.class;
        } else if (t == boolean.class) {
            return Boolean.class;
        } else {
            throw new ClassCastException("Class: " + t.getSimpleName() + " is not a primitive!");
        }
    }
}

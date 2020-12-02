package org.mybeanframework.core.bean;

import org.mybeanframework.core.util.SetBeanUtils;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 主要用于解析类上的@SetBean注解，并为有该注解的属性注入实例
 *
 * @author herenpeng
 */
public class SetBeanFactory {

    /**
     * 传入所有被管理的BeanMap，返回属性注入完成的BeanMap
     *
     * @param beanMap 被管理的BeanMap
     * @return 属性注入完成的BeanMap
     */
    protected static Map<String, Object> produce(Map<String, Object> beanMap) {
        try {
            Set<Map.Entry<String, Object>> entries = beanMap.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                Object currentObject = entry.getValue();
                Class<?> currentBeanClass = currentObject.getClass();
                Set<Field> fields = SetBeanUtils.getFieldNames(currentBeanClass);
                for (Field field : fields) {
                    field.setAccessible(true);
                    String key = SetBeanUtils.getAnnotationValue(field);
                    Object fieldBeanObject = null;
                    if (key != null && key.length() > 0) {
                        fieldBeanObject = beanMap.get(key);
                    } else {
                        fieldBeanObject = findFieldBeanObject(beanMap, field.getType());
                    }
                    field.set(currentObject, fieldBeanObject);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return beanMap;
    }

    /**
     * 查找BeanMap中的同一个类或者这个类的子类或实现
     *
     * @param beanMap   所有被MyBean管理的BeanMap
     * @param fieldType 需要查找的类的字节码对象
     * @return
     */
    private static Object findFieldBeanObject(Map<String, Object> beanMap, Class<?> fieldType) {
        Set<Map.Entry<String, Object>> entries = beanMap.entrySet();
        Iterator<Map.Entry<String, Object>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            Object currentObject = entry.getValue();
            Class<?> currentBeanClass = currentObject.getClass();
            if (currentBeanClass == fieldType || fieldType.isAssignableFrom(currentBeanClass)) {
                return currentObject;
            }
        }
        return null;
    }


}

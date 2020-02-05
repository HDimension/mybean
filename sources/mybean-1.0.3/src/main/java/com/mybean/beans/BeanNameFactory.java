package com.mybean.beans;

import com.mybean.util.MyBeanUtils;
import com.mybean.util.PackageScanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 主要用于解析properties文件和类上的@MyBean注解
 * @author hrp
 */
public class BeanNameFactory {

    /**
     * 注解包扫描name
     */
    private final static String PACKAGE_SCAN = "package.scan";

    /**
     * 全限定类名的Map集合，其中key为类名首字母小写，value为全限定类名
     */
    private static Map<String, String> beanNameMap = new HashMap<>();

    /**
     * BeanNameFactory的生产方法，将配置文件的信息和类上的@MyBean注解，
     * 全部转换为key为类名首字母小写，value为全限定类名的Map
     *
     * @param inputStream 一个输入流
     * @return 返回一个key为类名首字母小写，value为全限定类名
     */
    protected static Map<String, String> produce(InputStream inputStream) {
        try {
            Properties properties = new Properties();
            properties.load(inputStream);
            Set<String> nameSet = properties.stringPropertyNames();
            for (String name : nameSet) {
                if (PACKAGE_SCAN.equals(name)) {
                    Map<String, String> packageScanBeanNameMap = PackageScanUtils.getBeanNameMap(properties.getProperty(name));
                    beanNameMap.putAll(packageScanBeanNameMap);
                } else {
                    beanNameMap.put(name, properties.getProperty(name));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return beanNameMap;
    }


}

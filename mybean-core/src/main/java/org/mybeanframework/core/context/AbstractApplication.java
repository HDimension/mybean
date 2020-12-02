package org.mybeanframework.core.context;

import org.mybeanframework.core.BeanCoreContainer;

import java.io.IOException;

/**
 * Application的抽象实现，同时继承了CoreBeanMap
 * 使用的是类的适配器模式的设计思想，将接口和抽象类适配
 *
 * @author herenpeng
 * @since 2020-2-5 9:34
 */
public abstract class AbstractApplication extends BeanCoreContainer implements Application {

    @Override
    public <T> T getBean(String name) {
        return (T) beanCoreContainer.get(name);
    }

    @Override
    public <T> T getBean(String name, Class<T> objClass) {
        Object obj = beanCoreContainer.get(name);
        return objClass.isInstance(obj) ? objClass.cast(obj) : null;
    }

    @Override
    public void close() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
package com.mybean.annotation;

import java.lang.annotation.*;

/**
 * 注解，作用于属性上，用于给属性赋值
 *
 * @author hrp
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetBean {

    /**
     * value属性，用于指定需要赋值的类的id，
     * 有该属性则按id名称注入
     * 没有该属性则默认按类型注入
     *
     * @return id名称
     */
    String value() default "";
}

package com.mszlu.blog.common.aop;

import java.lang.annotation.*;

/**
 * @author by away
 * @date 2022/1/8 16:11
 *
 * TYPE: 可以放在类上
 * METHOD:可以放在类上
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented


public @interface LogAnnotation {
    String module() default "";

    String operator() default "";
}

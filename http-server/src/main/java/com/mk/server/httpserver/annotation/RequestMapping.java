package com.mk.server.httpserver.annotation;

import com.mk.server.httpserver.model.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface RequestMapping {
    String value() ;
    Method[] method() default  {};
}

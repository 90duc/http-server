package com.mk.server.httpserver.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE})
public @interface HttpServerBoot {

    int port() default 8088;
    int threadNum() default 10;
    String charset() default "utf-8";
}

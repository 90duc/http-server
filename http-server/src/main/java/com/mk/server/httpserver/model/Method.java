package com.mk.server.httpserver.model;

public enum Method {
    POST("POST"),
    GET("GET");

    public static Method valueByName(String name){
        for (Method m: values()){
            if(m.name.equalsIgnoreCase(name))
                return m;
        }
        return null;
    }
    private final String name;

    Method(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

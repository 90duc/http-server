package com.mk.server.httptest;


import com.mk.server.httpserver.HttpServer;
import com.mk.server.httpserver.annotation.HttpServerBoot;

@HttpServerBoot(port = 8000)
public class App 
{
    public static void main(String[] args)
    {
        HttpServer.run(App.class, args);
    }
}

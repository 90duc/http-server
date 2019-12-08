package com.mk.server.httptest.servlet;


import com.mk.server.httpserver.annotation.RequestMapping;
import com.mk.server.httpserver.model.HttpRequest;
import com.mk.server.httpserver.model.HttpResponse;
import com.mk.server.httpserver.servlet.HttpServlet;

@RequestMapping("/a")
public class AServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setBody("test a");
    }
}

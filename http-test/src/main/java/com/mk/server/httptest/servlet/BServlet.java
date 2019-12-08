package com.mk.server.httptest.servlet;


import com.mk.server.httpserver.annotation.RequestMapping;
import com.mk.server.httpserver.model.ContentType;
import com.mk.server.httpserver.model.HttpRequest;
import com.mk.server.httpserver.model.HttpResponse;
import com.mk.server.httpserver.servlet.HttpServlet;

@RequestMapping(value = "/b")
public class BServlet implements HttpServlet {

    @Override
    public void service(HttpRequest request, HttpResponse response) {
        response.setBody("test b");
    }
}

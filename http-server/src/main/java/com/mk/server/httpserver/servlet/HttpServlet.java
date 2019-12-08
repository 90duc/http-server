package com.mk.server.httpserver.servlet;


import com.mk.server.httpserver.model.HttpRequest;
import com.mk.server.httpserver.model.HttpResponse;

public interface HttpServlet {

    void service(HttpRequest request, HttpResponse response);
}

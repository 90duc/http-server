package com.mk.server.httpserver.model;

import com.mk.server.httpserver.annotation.RequestMapping;
import com.mk.server.httpserver.servlet.HttpServlet;

public class Handler {

    private RequestMapping requestMapping;
    private HttpServlet httpServlet;

    public Handler(RequestMapping requestMapping, HttpServlet httpServlet) {
        this.requestMapping = requestMapping;
        this.httpServlet = httpServlet;
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }

    public HttpServlet getHttpServlet() {
        return httpServlet;
    }

    public void setHttpServlet(HttpServlet httpServlet) {
        this.httpServlet = httpServlet;
    }
}

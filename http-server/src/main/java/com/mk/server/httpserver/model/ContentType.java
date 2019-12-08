package com.mk.server.httpserver.model;


public enum  ContentType {

   TEXT_HTML("text/html"),
   TEXT_PLAIN("text/plain"),
   TEXT_XML("text/xml"),
   IMAGE_GIF("image/gif"),
   IMAGE_JPG("image/jpeg"),
   IMAGE_PNG("image/png"),
   APPLICATION_OCTET_STREAM("application/octet-stream"),
   APPLICATION_PDF("application/pdf"),
   APPLICATION_MSWORD("application/msword"),
   APPLICATION_XML("application/xml"),
   APPLICATION_JSON("application/json");


    private String name;

    private ContentType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}

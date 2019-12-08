package com.mk.server.httpserver.model;

import java.nio.charset.Charset;

public class HttpResponse {

    private ContentType contentType = ContentType.TEXT_HTML;
    private HttpCode httpCode = HttpCode.CODE_200;
    private Charset encoding = null;
    private String body = "";


    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public HttpCode getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(HttpCode httpCode) {
        this.httpCode = httpCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Charset getEncoding() {
        return encoding;
    }

    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }
}

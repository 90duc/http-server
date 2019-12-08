package com.mk.server.httpserver.model;

import com.mk.server.httpserver.util.URLUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private Method method;
    private String host = "127.0.0.1";
    private int port = 80;
    private String uri = "/";
    private String path = "/";
    private String query = "";
    private String body = "";
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> uriParams;

    public void init(InputStream inSocket) throws IOException {


        String uriMethodLine = readHeadLine(inSocket);

        String[] uriMethod = uriMethodLine.split(" ");
        uri = uriMethod[1].trim();
        int qIndex = uri.indexOf("?");
        if (qIndex >= 0) {
            path = uri.substring(0, qIndex);
            query = uri.substring(qIndex + 1);
        } else {
            path = uri;
        }
        method = Method.valueByName(uriMethod[0]);

        String headLine;
        while ((headLine = readHeadLine(inSocket)).length() > 0) {
            int s = headLine.indexOf(": ");
            String key = headLine.substring(0, s);
            String value = headLine.substring(s + 2);
            headers.put(key, value);
        }

        byte[] data;
        int length;
        try {
            length = Integer.parseInt(headers.get("Content-Length"));
        } catch (Exception ex) {
            length = inSocket.available();
        }

        if(length>0) {
            data = new byte[length];
            length = inSocket.read(data);
            body = new String(data, 0, length, Charset.forName("utf-8"));
        }

        String _hostPort = headers.get("Host");
        if (_hostPort != null && _hostPort.length() > 0) {
            String[] hostPort = _hostPort.split(":");
            host = hostPort[0];
            if (hostPort[1].length() > 0)
                port = Integer.parseInt(hostPort[1]);
        }

        uriParams = URLUtil.requestUriParam(uri);
        String contentType = headers.get("Content-Type");
        if (contentType == null || contentType.length() == 0) {
            contentType = "application/x-www-form-urlencoded;charset=utf-8";
        }
        if (Method.POST.equals(method) && contentType.startsWith("application/x-www-form-urlencoded")) {
            Map<String, String> bodyParams = URLUtil.requestUriParam(body);
            uriParams.putAll(bodyParams);
        }
    }

    private String readHeadLine(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int pre =inputStream.read();
        int v = inputStream.read();
        while (pre!=-1 && !(pre=='\r'&& v=='\n')){
            baos.write(pre);
            pre = v;
            v = inputStream.read();
        }
        return new String(baos.toByteArray());
    }

    private byte[] readAll(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int v = inputStream.read();
        while (v!=-1){
            baos.write(v);
            v = inputStream.read();
        }
        return baos.toByteArray();
    }

    public String getParameter(String value) {
        return uriParams.get(value);
    }

    public Method getMethod() {
        return method;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUri() {
        return uri;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public String getBody() {
        return body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getUriParams() {
        return uriParams;
    }
}

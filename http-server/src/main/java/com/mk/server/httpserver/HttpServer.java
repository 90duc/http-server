package com.mk.server.httpserver;


import com.mk.server.httpserver.annotation.HttpServerBoot;
import com.mk.server.httpserver.annotation.RequestMapping;
import com.mk.server.httpserver.model.*;
import com.mk.server.httpserver.servlet.HttpServlet;
import com.mk.server.httpserver.util.ClassUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private static Charset defaultCharset;
    private static ExecutorService threadPool;
    private static Map<String, Handler> servletMap = new HashMap<>() ;

    public static void run(Class clas, String[] args)
    {
        HttpServerBoot boot = (HttpServerBoot) clas.getAnnotation(HttpServerBoot.class);
        if(boot == null){
            throw new RuntimeException("没有启动类");
        }
        try {
            List<Class> list = ClassUtil.getClasses(clas, RequestMapping.class);
            for(Class c:list){
                RequestMapping an = (RequestMapping)c.getAnnotation(RequestMapping.class);
                servletMap.put(an.value(), new Handler(an,(HttpServlet)c.newInstance()));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        defaultCharset = Charset.forName(boot.charset());
        Objects.requireNonNull(defaultCharset);
        threadPool = Executors.newFixedThreadPool(boot.threadNum()>1000?1000: boot.threadNum());

        start(boot.port()<1000?8088: boot.port());
    }

    private static void start(int port) {
        ServerSocket serverSocket;
        try {

            serverSocket = new ServerSocket(port);
            //死循环不间断监听客户端请求
            while (true) {
                final Socket socket = serverSocket.accept();
                //并发处理HTTP客户端请求
                service(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void service(Socket socket) {
        threadPool.submit(() -> {
            InputStream inSocket;
            try {
                //获取HTTP请求头
                inSocket = socket.getInputStream();
                HttpRequest request = new HttpRequest();
                //将响应头发送给客户端
                HttpResponse res = null;
                try {
                    request.init(inSocket);
                    res = serviceHttp(request);
                } catch (Exception ex) {
                    res = new HttpResponse();
                    res.setHttpCode(HttpCode.CODE_500);
                    res.setBody(ex.getMessage());
                }

                int length = 0;
                byte[] d = new byte[0];
                if (Objects.nonNull(res.getBody())) {
                    d = res.getBody().getBytes(Charset.forName("utf-8"));
                    length = d.length;
                }
                String responseFirstLine = "HTTP/1.1 " + res.getHttpCode().getCode() + " " + res.getHttpCode().getMsg() + "\r\n";

                Charset encoding = Optional.ofNullable(res.getEncoding()).orElse(defaultCharset);
                String responseHead = "Content-Type:" + res.getContentType() + ";charset=" + encoding.name() + "\r\n";
                String responseLength = "Content-Length:" + length + "\r\n";
                OutputStream outSocket = socket.getOutputStream();

                outSocket.write(responseFirstLine.getBytes());
                outSocket.write(responseHead.getBytes());
                outSocket.write(responseLength.getBytes());
                outSocket.write("\r\n".getBytes());
                if(length>0){
                    outSocket.write(d);
                }
                outSocket.flush();
                outSocket.close();
                inSocket.close();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    private static HttpResponse serviceHttp(HttpRequest request) {
        HttpResponse response = new HttpResponse();

        Handler handler = servletMap.get(request.getPath());

        if (handler == null) {
            response.setHttpCode(HttpCode.CODE_404);
            response.setContentType(ContentType.TEXT_HTML);
            response.setBody("");
        }else {
            RequestMapping requestMapping =  handler.getRequestMapping();
            Method[] methods =requestMapping.method();
            if(methods.length>0 && !Arrays.asList().contains(request.getMethod())){
                response.setHttpCode(HttpCode.CODE_404);
                response.setContentType(ContentType.TEXT_HTML);
                response.setBody("");
            }else {

                try {
                    handler.getHttpServlet().service(request, response);
                } catch (Exception ex) {
                    response.setContentType(ContentType.TEXT_HTML);
                    response.setHttpCode(HttpCode.CODE_500);
                    response.setBody(ex.getMessage());
                }
            }
        }
        return response;
    }
}

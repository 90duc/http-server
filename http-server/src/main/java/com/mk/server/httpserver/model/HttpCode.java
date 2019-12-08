package com.mk.server.httpserver.model;

public enum HttpCode {
    CODE_200(200,"OK"),
    CODE_404(404,"Not Found"),
    CODE_500(500,"Internal Server Error");

    private int code;
    private String msg;
    HttpCode(int code, String msg){
        this.code =code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

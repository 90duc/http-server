package com.mk.server.httpserver.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class URLUtil {

    private static String getUrlParam(String url) {
        String queryStr = null;
        url = url.trim();
        String[]  uriQuery = url.split("[?]");
        if (url.length() > 1) {
            if (uriQuery.length > 1) {
                if (uriQuery[1] != null) {
                    queryStr = uriQuery[1];
                }
            }
        }

        return queryStr;
    }


    public static Map<String, String> requestUriParam(String uri) {

        String strUrlParam = getUrlParam(uri);
        if (strUrlParam == null) {
            return Collections.EMPTY_MAP;
        }
        return requestParam(strUrlParam);
    }

    public static Map<String, String> requestParam(String query) {
        Map<String, String> mapRequest = new HashMap<String, String>();


        String queryStr = query;
        if (queryStr == null|| (queryStr=queryStr.trim()).length()==0) {
            return Collections.EMPTY_MAP;
        }

        String[] params = queryStr.split("[&]");
        for (String param : params) {
            String[] keyValue = param.split("[=]");
            //解析出键值
            if (keyValue.length > 1) {
                //正确解析
                String value = null;
                try {
                    value = URLDecoder.decode(keyValue[1], "utf-8");
                } catch (UnsupportedEncodingException e) {
                    value = keyValue[1];
                }
                mapRequest.put(keyValue[0], value);

            } else {
                if (keyValue[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(keyValue[0], "");
                }
            }
        }
        return mapRequest;
    }
}

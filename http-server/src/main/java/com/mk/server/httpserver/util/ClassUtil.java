package com.mk.server.httpserver.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil {

    public static List<Class> getClasses(Class jarClass,Class anClass) {
        List<Class> classList = new ArrayList<>();
        String url = getClassPath(jarClass);
        List<String> classes = getClassesList(url);
        for(String clas : classes) {
            try {
                Class c = Class.forName(clas);
                Annotation an = c.getDeclaredAnnotation(anClass);
                if (an != null) {
                    classList.add(c);
                }
            }catch (ClassNotFoundException ex){

            }
        }

        return classList;
    }

    private static String getClassPath(Class clas) {
        String url = clas.getResource("/").getFile();
        if (url.startsWith("/")) {
            url = url.replaceFirst("/", "");
        }
        url = url.replaceAll("/", "\\\\");
        return url;
    }

    private static List<String> getClassesList(String url) {
        File file = new File(url);
        List<String> classes = getAllClass(file);
        for (int i = 0; i < classes.size(); i++) {
            classes.set(i, classes.get(i).replace(url, "").replace(".class", "").replace("\\", "."));
        }
        return classes;
    }
    private static List<String> getAllClass(File file) {
        List<String> ret = new ArrayList<>();
        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (File i : list) {
                List<String> j = getAllClass(i);
                ret.addAll(j);
            }
        } else {

            ret.add(file.getAbsolutePath());
        }
        return ret;
    }

}

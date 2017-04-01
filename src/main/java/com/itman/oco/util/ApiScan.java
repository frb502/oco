package com.itman.oco.util;

import com.itman.oco.api.ApiBase;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by furongbin on 16/10/12.
 */
public class ApiScan {
    private static Map<String, Servlet> apis = new HashMap<String, Servlet>();

    static {
        PackageScan ps = new PackageScan("com.itman.oco.api");

        List<String> classNames = null;
        try {
            classNames = ps.getFullyQualifiedClassNameList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null != classNames && !classNames.isEmpty()) {
            for (String className : classNames) {
                try {
                    Class clazz = ps.getClassLoader().loadClass(className);
                    ApiPath path = (ApiPath) clazz.getAnnotation(ApiPath.class);
                    if (null != path) {
                        apis.put(path.value(), (Servlet)clazz.newInstance());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static Map<String, Servlet> getApis() {
        return apis;
    }

}

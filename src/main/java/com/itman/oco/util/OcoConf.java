package com.itman.oco.util;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
/**
 * Created by furongbin on 17/3/31.
 */
public class OcoConf {
    private static String configFile = "conf/app.conf";
    private static Config conf = null;
    static {
        String userConfig = "conf/"+ System.getProperty("user.name") + ".conf";
        File file = new File(userConfig);
        if (file.exists()) {
            configFile = userConfig;
        }
        System.setProperty("config.file", configFile);
        conf = ConfigFactory.load();
    }

    private static <T> T getConf(String name, T defaultVal) {
        try {
            return (T)conf.getValue(name).unwrapped();
        } catch (Exception e) {
            return defaultVal;
        }

    }

    private static <T> T getConf(String name) {
        return (T)conf.getValue(name).unwrapped();
    }

    public static String host = getConf("server.host", "0.0.0.0");

    public static int port = getConf("server.port", 3899);

    public static int maxThreads = getConf("server.maxThreads", 100);
}

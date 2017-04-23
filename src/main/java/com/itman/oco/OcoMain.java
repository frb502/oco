package com.itman.oco;

import com.itman.oco.service.RestServer;
import com.itman.oco.util.OcoConf;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by furongbin on 17/3/31.
 */
public class OcoMain {
    public static void main(String[] args) {
        PropertyConfigurator.configure("conf/log4j.properties");
        final RestServer restServer = RestServer.createServer(OcoConf.host, OcoConf.port);
        restServer.setMaxThreads(OcoConf.maxThreads);
        try {
            restServer.start();
            Runtime.getRuntime().addShutdownHook(new Thread(){
                public void run() {
                    restServer.stop();
                }
            });
            Thread.currentThread().join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

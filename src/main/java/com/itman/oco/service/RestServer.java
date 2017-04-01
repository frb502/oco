package com.itman.oco.service;

import com.itman.oco.api.ApiBase;
import com.itman.oco.util.ApiScan;
import com.itman.oco.util.LazyLogging;
import com.itman.oco.util.PidUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;

/**
 * Created by furongbin on 17/3/30.
 */
public class RestServer implements LazyLogging{

    private String serverName = "  ___   ____ ___\n" +
            " / _ \\ / ___/ _ \\\n" +
            "| | | | |  | | | |\n" +
            "| |_| | |__| |_| |\n" +
            " \\___/ \\____\\___/\n\n" + "         _             _ _\n" +
            "        | |__  _   _  (_) |_ _ __ ___   __ _ _ __\n" +
            " _____  | '_ \\| | | | | | __| '_ ` _ \\ / _` | '_ \\\n" +
            "|_____| | |_) | |_| | | | |_| | | | | | (_| | | | |\n" +
            "        |_.__/ \\__, | |_|\\__|_| |_| |_|\\__,_|_| |_|\n" +
            "               |___/\n\n" + new Date().toString();

    private String index ="<!DOCTYPE html> <body style=\"background-color: black;color: greenyellow;\">\n" +
            "    <pre>"+ serverName +"</pre>\n" +
            "    </body>";

    private String hostName;

    private int port;

    private int maxThreads = 100;

    private Server server;

    private RestServer(){}

    private RestServer(String hostName, int port){
        this.hostName = hostName;
        this.port = port;
    }
    private RestServer(String hostName, int port, String serverName, int maxThreads){
        this(hostName, port);
        this.serverName = serverName;
        this.maxThreads = maxThreads;
    }

    public static RestServer createServer(String hostName, int port) {
        return new RestServer(hostName, port);
    }

    public static RestServer createServer(String hostName, int port, String serverName, int maxThreads) {
        return new RestServer(hostName, port, serverName, maxThreads);
    }

    public void setMaxThreads(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    public void start() throws Exception {
        server = new Server(new InetSocketAddress(hostName, port));
        QueuedThreadPool pool = new QueuedThreadPool();
        pool.setMaxThreads(maxThreads);
        pool.setDaemon(true);
        server.setThreadPool(pool);
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        initApi(contextHandler);
        HandlerList handlerList = new HandlerList();
        handlerList.addHandler(contextHandler);
        server.setHandler(handlerList);
        try {
            server.start();
            logger.info("\n" + serverName +" server start listen at: " + port + ", maxThreads: "
                    + maxThreads + ", pid is " + PidUtils.getPid());
            server.join();
        } catch (Exception e) {
            try {
                server.stop();
                pool.stop();
            } catch (Exception e1) {

            }
            throw e;
        }
    }

    public void stop() {
        if (null != server) {
            try {
                server.stop();
            } catch (Exception e) {

            }
        }
    }

    private void initApi(ServletContextHandler contextHandler) {
        Servlet servlet = new ApiBase("text/html") {
            @Override
            protected String doService(HttpServletRequest request) {
                return index;
            }
        };
        ServletHolder holder = new ServletHolder(servlet);
        contextHandler.addServlet(holder, "/");
        for (Map.Entry<String, Servlet> entry: ApiScan.getApis().entrySet()) {
            holder = new ServletHolder(entry.getValue());
            contextHandler.addServlet(holder, entry.getKey());
        }
    }
}

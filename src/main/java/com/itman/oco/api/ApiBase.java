package com.itman.oco.api;
import com.itman.oco.exception.OcoException;
import com.itman.oco.json.JSONObject;
import com.itman.oco.util.LazyLogging;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by furongbin on 17/3/29.
 */
abstract public class ApiBase extends HttpServlet implements LazyLogging{

    private String contentType = "text/json";
    private Pattern lineRegexPattern = Pattern.compile("\t|\r|\n");
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public ApiBase() {}

    public ApiBase(String contentType) {
        this.contentType = contentType;
    }

    protected String getParam(String name) {
        return request.getParameter(name);
    }

    protected String getParam(String name, String defaultVal) {
        String param =  request.getParameter(name);
        if (null == param) {
            param = defaultVal;
        }
        return param;
    }

    protected void writeResult(String result) throws IOException {
        response.getWriter().println(result);
    }

    protected String getParam(String name, Boolean isRequired) {
        String param =  request.getParameter(name);
        if (isRequired && null == param) {
            throw new OcoException("Missing parameter : "+ name);
        }
        return param;
    }

    abstract protected void doService() throws IOException;

    private void doGetOrPost(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        String requestId = UUID.randomUUID().toString();
        long start = System.currentTimeMillis();
        Boolean isSuccess = true;
        String errorMsg = null;
        int statusCode = 0;
        try {
            logRequest(request, requestId);
            response.setContentType("%s;charset=utf-8".format(contentType));
            response.setStatus(HttpServletResponse.SC_OK);
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Server", "oco");
            doService();
        } catch (Throwable e) {
            errorMsg = e.getMessage();
            isSuccess = false;
            statusCode = 1;
        } finally {
            if (!isSuccess) {
                String errMsg = request.getRequestURI() + " " + getRequestParam(request) + " " + "error: " + errorMsg;
                logger.error(errMsg);
                JSONObject jsonObj = new JSONObject();
                try {
                    jsonObj.put("status", Integer.toString(statusCode));
                    jsonObj.put("msg", errorMsg);
                    response.getWriter().println(jsonObj.toString());
                } catch (Exception e) {
                }

            }
        }

        // 记录请求结束时间，便于分析请求处理耗时，排查问题
        if (request.getRequestURI() != "/") {
            logger.info("Request end: " + requestId + " " + isSuccess + " " +
                    (System.currentTimeMillis() - start) + "ms");
        }
    }

    private void logRequest(HttpServletRequest request, String requestId) {
        if (request.getRequestURI() == "/") {
            return;
        }
        // 去掉参数里的制表, 回车, 换行符
        final Matcher m = lineRegexPattern.matcher(getRequestParam(request));
        String nginxIp = request.getHeader("X-Real-Ip");
        if (nginxIp == null) {nginxIp = request.getRemoteHost();};
        logger.info(nginxIp + " " + request.getMethod() + " " +  request.getRequestURI() + " "
                + "?" + m.replaceAll(" ") + " [" + requestId + "]");
    }

    private String getRequestParam(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            sb.append(paramName);
            sb.append('=');
            sb.append(paramValue);
            sb.append('&');
        }
        if (sb.length() > 1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGetOrPost(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGetOrPost(req, resp);
    }
}

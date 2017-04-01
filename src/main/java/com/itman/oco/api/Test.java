package com.itman.oco.api;

import com.itman.oco.util.ApiPath;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by furongbin on 17/4/1.
 */
@ApiPath("/test")
public class Test extends ApiBase {
    @Override
    protected String doService(HttpServletRequest request) {
        return "hello world";
    }
}

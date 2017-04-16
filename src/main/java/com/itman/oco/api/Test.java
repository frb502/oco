package com.itman.oco.api;

import com.itman.oco.util.ApiPath;


/**
 * Created by furongbin on 17/4/1.
 */
@ApiPath("/test")
public class Test extends JsonBase {

    @Override
    protected String doService0() {
        return "hello world";
    }
}

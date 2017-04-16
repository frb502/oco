package com.itman.oco.api;

import java.io.IOException;

/**
 * Created by furongbin on 17/4/16.
 */
abstract public class JsonBase extends ApiBase{

    public JsonBase() {
        super("text/json");
    }

    abstract String doService0();

    @Override
    protected void doService() throws IOException {
        this.writeResult(doService0());
    }
}

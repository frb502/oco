package com.itman.oco.api.base;

import java.io.IOException;

/**
 * Created by furongbin on 17/4/16.
 */
abstract public class JsonBase extends ApiBase {

    public JsonBase() {
        super("text/json");
    }

    abstract protected String doService0();

    @Override
    protected void doService() throws IOException {
        this.writeResult(doService0());
    }
}

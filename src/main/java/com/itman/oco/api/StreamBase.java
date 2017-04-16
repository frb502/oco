package com.itman.oco.api;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by furongbin on 17/4/16.
 */
abstract public class StreamBase extends ApiBase {

    public StreamBase() {
        super("application/octet-stream");
    }

    protected void writeStream(InputStream in) throws IOException {
        OutputStream out = response.getOutputStream();
        byte buf[] = new byte[4096];
        int bytesRead = in.read(buf);
        while (bytesRead >= 0) {
            out.write(buf, 0, bytesRead);
            bytesRead = in.read(buf);
        }
        out.flush();
    }

    protected InputStream getStream() throws IOException {
        return  request.getInputStream();
    }
}

package com.itman.oco.api;

import com.itman.oco.OcoEnv;
import com.itman.oco.api.base.StreamBase;
import com.itman.oco.exception.ExceptionCode;
import com.itman.oco.exception.OcoException;
import com.itman.oco.util.ApiPath;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by furongbin on 17/4/25.
 */
@ApiPath("/file/download")
public class FileDownload extends StreamBase{

    public void doService() throws IOException {
        String tokenId = getParam("token", true);
        String filePath = getParam("path", true);
        if(!OcoEnv.accountManager.verifyToken(tokenId)) {
            throw new OcoException("token invalid", ExceptionCode.TOKEN_EXPIRE);
        }
        File file = new File(filePath);
        if (!file.exists()) {
            throw new OcoException("file no exits", ExceptionCode.ERROR);
        }
        String simpleName = file.getName();
        setHeader("Content-Disposition", "attachment; filename=" + simpleName);
        FileInputStream fis = new FileInputStream(file);
        writeStream(fis);
        fis.close();
    }
}

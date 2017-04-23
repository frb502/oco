package com.itman.oco.util;

import com.cloopen.rest.sdk.CCPRestSmsSDK;

import java.util.HashMap;

/**
 * Created by furongbin on 17/4/22.
 */
public class SmsHelper implements LazyLogging {
    private static CCPRestSmsSDK restAPI;

    static {
        restAPI = new CCPRestSmsSDK();
        restAPI.init(OcoConf.smsIP, OcoConf.smsPort);
        restAPI.setAccount(OcoConf.accountSid, OcoConf.accountToken);
        restAPI.setAppId(OcoConf.appId);
    }

    public static int send(String phoneNumber, String content) {
        HashMap<String, Object> result = restAPI.sendTemplateSMS(phoneNumber,
                OcoConf.templateId, new String[]{content,"5"});
        if("000000".equals(result.get("statusCode"))){
            //正常返回输出data包体信息（map）
            HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
            logger.info("[SmsHelper] send successfull, phoneNmber=" + phoneNumber + ", content="+content);
            return 0;
        }else{
            //异常返回输出错误码和错误信息
            logger.error("[SmsHelper] send fail, statusCode=" +
                    result.get("statusCode") +" errMsg= "+result.get("statusMsg"));
            return 1;
        }
    }
}

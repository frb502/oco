package com.itman.oco.api;

import com.itman.oco.OcoEnv;
import com.itman.oco.api.base.JsonBase;
import com.itman.oco.exception.ExceptionCode;
import com.itman.oco.exception.OcoException;
import com.itman.oco.json.JSONObject;
import com.itman.oco.manager.AccountManager;
import com.itman.oco.util.ApiPath;
import com.itman.oco.util.UUIDUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by furongbin on 17/4/23.
 */
@ApiPath("/user/login")
public class Login extends JsonBase {

    public String doService0() {
        String accountId = getParam("account", true);
        String code = getParam("code", true);
        if (!OcoEnv.accountManager.verifyAccount(accountId)) {
            throw new OcoException("account no exits", ExceptionCode.ACCOUNT_NO_EXITS);
        }
        if (!OcoEnv.accountManager.verifyCode(accountId, code)) {
            throw new OcoException("code invalid", ExceptionCode.INVALID_CODE);
        }
        String tokenId = UUIDUtils.uuid();
        AccountManager.Token token = new AccountManager.Token(accountId, tokenId, System.currentTimeMillis(), 86400000);
        OcoEnv.accountManager.updateToken(accountId, token);
        JSONObject jsonObj = new JSONObject();
        JSONObject result = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int status = ExceptionCode.OK;
        Date expireDate = OcoEnv.accountManager.getAccount(accountId).user.getExpireDate();
        long currentTime = System.currentTimeMillis();
        long expireTime = expireDate.getTime();
        String msg = "";
        if (currentTime >= expireTime) {
            status = ExceptionCode.ARREARAGE;
            msg = "account arrearage";
        }
        long validTime = Math.min(86400, (expireTime -currentTime)/1000);
        try {
            result.put("token", tokenId);
            result.put("validTime", validTime);
            result.put("expireTime", df.format(expireDate));
            jsonObj.put("status", status);
            jsonObj.put("msg", msg);
            jsonObj.put("result", result);
        } catch (Exception e) {
            throw new OcoException("Server Internal Error", ExceptionCode.ERROR);
        }
        return jsonObj.toString();
    }
}

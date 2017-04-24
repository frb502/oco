package com.itman.oco.exception;

/**
 * Created by furongbin on 17/4/23.
 */
public class ExceptionCode {
    public final static int OK = 0;  //请求OK
    public final static int ERROR = 1; //请求错误
    public final static int PARAMETER_MISSING = 3; //参数缺失
    public final static int TOKEN_EXPIRE = 4; // token过期
    public final static int ACCOUNT_NO_EXITS = 5; // 账号不存在
    public final static int INVALID_CODE = 6; // 无效验证码
}

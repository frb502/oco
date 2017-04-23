package com.itman.oco.api;

import com.itman.oco.OcoEnv;
import com.itman.oco.api.base.JsonBase;
import com.itman.oco.dao.UserDao;
import com.itman.oco.exception.ExceptionCode;
import com.itman.oco.exception.OcoException;
import com.itman.oco.json.JSONObject;
import com.itman.oco.manager.AccountManager;
import com.itman.oco.model.User;
import com.itman.oco.util.ApiPath;
import com.itman.oco.util.SmsHelper;
import com.itman.oco.util.SqlSessionHelper;
import com.itman.oco.util.UUIDUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * Created by furongbin on 17/4/23.
 */
@ApiPath("/user/verify")
public class AccountVerify extends JsonBase {

    private SqlSession sqlSession = SqlSessionHelper.openSession();
    public String doService0() {
        String accountId = getParam("account", true);
        UserDao dao = sqlSession.getMapper(UserDao.class);
        User user = dao.selectOne(accountId);
        if (null == user) {
            throw new OcoException("account no exits", ExceptionCode.ACCOUNT_NO_EXITS);
        }
        String code = UUIDUtils.randomCode();
        int status = SmsHelper.send(user.getTelephone(), code);
        if (status == 0) {
            AccountManager.Account account = new AccountManager.Account(user, code);
            OcoEnv.accountManager.addAccount(user.getAccount(), account);
        }
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("status", status);
            jsonObj.put("msg", "");
            jsonObj.put("result", "");
        } catch (Exception e) {
            throw new OcoException("Server Internal Error", ExceptionCode.ERROR);
        }

        return jsonObj.toString();
    }
}

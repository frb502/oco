package com.itman.oco.manager;

import com.itman.oco.exception.ExceptionCode;
import com.itman.oco.exception.OcoException;
import com.itman.oco.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by furongbin on 17/4/23.
 */
public class AccountManager {
    private  Map<String, Account> accounts = new ConcurrentHashMap<String, Account>();
    private  Map<String, Token> tokens = new ConcurrentHashMap<String, Token>();

    public void addAccount(String accountId, Account account) {
        removeAccount(accountId);
        accounts.put(accountId, account);
    }

    public Account getAccount(String accountId) {
        return accounts.get(accountId);
    }

    public boolean verifyAccount(String accountId) {
        return accounts.containsKey(accountId);
    }

    public boolean verifyCode(String accoundId, String code) {
        Account account = accounts.get(accoundId);
        if (account != null && account.verifyCode(code)) {
            account.invalidCode();
            return  true;
        }
        return false;
    }

    public void updateToken(String accountId, Token token) {
        if (accounts.containsKey(accountId)) {
            tokens.put(token.tokenId, token);
            accounts.get(accountId).tokenId = token.tokenId;
        } else {
            throw new OcoException("account no exits", ExceptionCode.ACCOUNT_NO_EXITS);
        }
    }

    public void removeAccount(String accountId) {
        if (accounts.containsKey(accountId)) {
            Account old = accounts.remove(accountId);
            if (old.tokenId != null && tokens.containsKey(old.tokenId)) {
                tokens.remove(old.tokenId);
            }
        }
    }

    public boolean verifyToken(String tokenId) {
        Token token = tokens.get(tokenId);
        if (token != null && !token.isExpire()) {
            return true;
        }
        return  false;
    }

    public static AccountManager create() {
        AccountManager manager = new AccountManager();
        return manager;
    }

    public static class Account {
        public User user;
        public String accountId;
        public String code;
        public String tokenId;
        private long createTime;
        public Account(User user, String code) {
            this.user = user;
            this.code = code;
            this.accountId = user.getAccount();
            this.createTime = System.currentTimeMillis();
        }

        public boolean verifyCode(String code) {
            long time = System.currentTimeMillis() - createTime;
            if (time < 300000 && this.code != null && this.code.equals(code)) {
                return true;
            }
            return false;
        }

        public void invalidCode() {
            this.code = null;
        }
    }

    public static class Token {
        public String account;
        public String tokenId;
        public long createTime;
        public long validTime;

        public Token(String account, String tokenId, long createTime, long validTime) {
            this.account = account;
            this.tokenId = tokenId;
            this.createTime = createTime;
            this.validTime = validTime;
        }

        public boolean isExpire() {
            long time = System.currentTimeMillis() - createTime;
            return time > validTime;
        }
    }
}

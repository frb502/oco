package com.itman.oco.model;

import java.util.Date;

/**
 * Created by furongbin on 17/4/22.
 */
public class User {
    private int id;
    private Date expireDate;
    private String userName;
    private double amount;
    private String telephone;
    private String account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", expireDate=" + expireDate +
                ", userName='" + userName + '\'' +
                ", amount=" + amount +
                ", telephone='" + telephone + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}

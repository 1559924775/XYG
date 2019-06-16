package com.example.a15599.xiaoyuangou;

import android.widget.Button;

import java.util.ArrayList;

/**
 * 这是订单类
 */
public class Order {


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public boolean ispStatus() {
        return pStatus;
    }

    private int orderId;
    private String userId;
    private String userTel;
    private String userMail;
    private String tatalPrice;
    private boolean pStatus;

    public String getdTime() {
        return dTime;
    }

    public void setdTime(String dTime) {
        this.dTime = dTime;
    }

    private String dTime;
    private ArrayList<Goods> goods;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getTatalPrice() {
        return tatalPrice;
    }

    public void setTatalPrice(String tatalPrice) {
        this.tatalPrice = tatalPrice;
    }

    public Boolean getpStatus() {
        return pStatus;
    }

    public void setpStatus(boolean pStatus) {
        this.pStatus = pStatus;
    }

    public ArrayList<Goods> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<Goods> goods) {
        this.goods = goods;
    }

    public String toString(){
        return "订单号："+orderId+" 用户id："+userId+"\r\n"+goods.toString();
    }
}

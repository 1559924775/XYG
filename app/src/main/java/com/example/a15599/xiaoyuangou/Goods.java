package com.example.a15599.xiaoyuangou;

import java.math.BigDecimal;

public class Goods {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;
    private String src;
    private BigDecimal price;
    private String describ;
    private String sellerId;
    private int num;
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }


    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }
    public String toString(){
        return "所属商户id："+sellerId+" 商品名："+name+" src："+src+" 单价："+price+" 描述："+describ+" 数量: "+num;
    }
}

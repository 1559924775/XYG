package com.example.a15599.xiaoyuangou;

import android.util.Log;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 这是一个购物车数据结构
 */
public class ShoppingCart implements Serializable{
    String userId;
    private Map<String,Goods> myCart=new ConcurrentHashMap<>();
    public ShoppingCart(){

    }
    public ShoppingCart(String userId){
        this.userId=userId;
    }
    public void add(String id,Goods good){
        if(myCart.containsKey(id)){
            Goods cartGoods=myCart.get(id);
            int num=cartGoods.getNum();
            cartGoods.setNum(num+1);
        }else{
            good.setNum(1);
            myCart.put(id,good);
        }
    }
    public void remove(String id){
        myCart.remove(id);
    }
    //更新num
    public void update(String id,String num){
        myCart.get(id).setNum(Integer.parseInt(num));
    }
    //遍历HashMap
    public ArrayList<Goods> showGoods(){
        ArrayList<Goods> Goodss=new ArrayList<>();
        Iterator<String> iterator=myCart.keySet().iterator();
        while(iterator.hasNext()){
            String id=iterator.next();
            //把引用复制出来
            Goods good=myCart.get(id);
            Goodss.add(good);
        }
        return Goodss;
    }
    //获得购物车中商品总价
    public BigDecimal getTotalPrice(){
        BigDecimal totalPrice=new BigDecimal(0);
        ArrayList<Goods> Goodss=showGoods();
        for(Goods good:Goodss){
            Log.d("Tag",good.getPrice()+" "+good.getNum());
            totalPrice=totalPrice.add(good.getPrice().multiply(new BigDecimal(good.getNum())));
            Log.d("Tag",totalPrice+"");
        }
        return totalPrice.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    //打印之前移除num为0的Goods
    public void check(){
        Iterator<String> iterator=myCart.keySet().iterator();
        while(iterator.hasNext()){
            String id=iterator.next();
            //把引用复制出来
            Goods Goods=myCart.get(id);
            //若在这直接删除，导致iterator大小变化。
            if(Goods.getNum()<1)myCart.remove(id);
        }
    }
    public String toString(){
        return "我是用户id为"+userId+"的购物车"+"\r\n"+super.toString();
    }
}

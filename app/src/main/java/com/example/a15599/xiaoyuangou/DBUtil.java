package com.example.a15599.xiaoyuangou;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by 15599 on 2018/4/4.
 */

public class DBUtil {
    SqlHelper sh;
    public DBUtil(){
        sh=new SqlHelper();
    }
    public boolean update(User user){
       // int id=user.getId();
        String name=user.getName();
        String pwd=user.getPwd();
        String email=user.getEmail();
        String tel=user.getTel();
        String adress=user.getAdress();
        Log.d("Tag",user.toString());
        String sql="insert into user_login (name,pwd,tel,email,adress)" +
                "values('"+name+"','"+pwd+"','"+tel+"','"+email+"','"+adress+"')";
        Log.d("Tag",sql);
        String[] param=null;
        try{
            sh.update1(sql,param);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
    public boolean updateUserInfo(User user){
        int id=user.getId();
        String name=user.getName();
        String pwd=user.getPwd();
        String email=user.getEmail();
        String tel=user.getTel();
        String adress=user.getAdress();
        Log.d("Tag",user.toString());
        String sql="update user_login set name=?,pwd=?,email=?,tel=?,adress=?  where id=?;";
        Log.d("Tag",sql);
        String[] param={name,pwd,email,tel,adress,id+""};
        try{
            sh.update1(sql,param);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
    public boolean update(Seller seller){
        int id=seller.getId();
        String name=seller.getName();
        String pwd=seller.getPwd();
        String email=seller.getEmail();
        String tel=seller.getTel();
        Log.d("Tag",seller.toString());
        String sql="insert into seller_login (name,pwd,tel,email)" +
                "values('"+name+"','"+pwd+"','"+tel+"','"+email+"');";
        Log.d("Tag",sql);
        String[] param=null;
        try{
            sh.update1(sql,param);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
    public boolean update(Goods goods){
        //int id=goods.getId();
        String name=goods.getName();
        String src=goods.getSrc();
        String price=goods.getPrice()+"";
        String describ=goods.getDescrib();
        String sellerId=goods.getSellerId();
        Log.d("Tag",goods.toString());
        String sql="insert into goods (name,src,price,describ,sellerId) values(?,?,?,?,?);";
        Log.d("Tag",sql);
        String[] param={name,src,price,describ,sellerId};
        try{
            sh.update1(sql,param);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
    public String check(User user){
        String id="-999";
        String name=user.getName();
        String pwd=user.getPwd();
        String sql="select id from user_login where name='"+name+"' and pwd='"+pwd+"'" ;
        String[] param=null;
        try{
            ArrayList<ArrayList<String>> result=sh.query(sql,param);
            id=result.get(0).get(0);
        }catch (RuntimeException e){
            Log.d("Tag","用户不存在");
            id="-999";
        }
        return id;
    }
    public User getUserById(String id){
        User user=null;
        String sql="select * from user_login where id=?";
        String[] param={id};
        try{
            ArrayList<ArrayList<String>> result=sh.query(sql,param);
            Log.d("Tag",result.get(0).toString());
            user=new User();
            user.setId(Integer.parseInt(result.get(0).get(0)));
            user.setName(result.get(0).get(1));
            user.setPwd(result.get(0).get(2));
            user.setTel(result.get(0).get(3));
            user.setEmail(result.get(0).get(4));
            user.setAdress(result.get(0).get(5));
        }catch (RuntimeException e){
            Log.d("Tag","用户不存在");

        }
        return user;
    }
    public String check(Seller seller){
        String id="-999";
        String name=seller.getName();
        String pwd=seller.getPwd();
        String sql="select id from seller_login where name='"+name+"' and pwd='"+pwd+"'" ;
        String[] param=null;
        try{
            ArrayList<ArrayList<String>> result=sh.query(sql,param);
            id=result.get(0).get(0);
        }catch (RuntimeException e){
            Log.d("Tag","用户不存在");
            id="-999";
        }
        return id;
    }
    public ArrayList<ArrayList<String>> queryGoods(){
        String sql="select * from goods;";
        String[] param=null;
        ArrayList<ArrayList<String>> result=sh.query(sql,param);
        if(result!=null){
            return result;
        }
        return null;
    }
    public ArrayList<ArrayList<String>> queryGoodsBySeller(String sellerId){
        String sql="select * from goods where sellerId=?;";
        String[] param={sellerId};
        ArrayList<ArrayList<String>> result=sh.query(sql,param);
        if(result!=null){
            return result;
        }
        return null;
    }
    public boolean deleteGoodsById(int id){
        String sql="delete from goods where id=?";
        String[] param={id+""};
        try{
            sh.update1(sql,param);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
    public boolean updateGoodsById(int id,String[] param){
        String sql="update goods set name=? ,price=?, describ=? where id="+id;
        try{
            sh.update1(sql,param);
        }catch (RuntimeException e){
            return false;
        }
        return true;
    }
    public ArrayList<ArrayList<String>> queryByGoodsName(String name){
        String sql="select * from goods where name like '%"+name+"%' ;";
        String[] param=null;
        ArrayList<ArrayList<String>> result=sh.query(sql,param);
        if(result!=null){
            return result;
        }
        return null;
    }
    public Goods getGoodsById(String id){
        String sql="select * from goods where id=?;";
        Goods goods=new Goods();
        String[] param={id};
        ArrayList<ArrayList<String>> result=sh.query(sql,param);
        if(result!=null){
            goods.setId(Integer.parseInt(result.get(0).get(0)));
            goods.setName(result.get(0).get(1));
            goods.setSrc(result.get(0).get(2));
            goods.setPrice(new BigDecimal(result.get(0).get(3)));
            goods.setDescrib(result.get(0).get(4));
            goods.setSellerId(result.get(0).get(5));
        }
        return goods;
    }
    /**
     * 下订单
     */
    public void order(Order order){
        OrderService orderService=new OrderService();
        orderService.confirmOrder(order);
    }

    /**
     * 查询所有订单
     * @return
     */
    public ArrayList<ArrayList<String>> queryOrders(){
        String sql="select * from orders;";
        String[] param=null;
        ArrayList<ArrayList<String>> result=sh.query(sql,param);
        if(result!=null){
            return result;
        }
        return null;
    }
    public ArrayList<ArrayList<String>> queryOrdersDetail(String orderId){
        String sql="select *from order_item where orderId=?;";
        String[] param={orderId};
        ArrayList<ArrayList<String>> result=sh.query(sql,param);
        if(result!=null){
            return result;
        }
        return null;
    }
}

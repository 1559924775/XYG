package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Order_detail extends AppCompatActivity {
    String orderId;
    String orderTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        //取出orderId
        Intent intent=getIntent();
        orderId= intent.getStringExtra("orderId");
        orderTime=intent.getStringExtra("orderTime");
        result=getOrderDetailById(orderId);
        TextView id=findViewById(R.id.orderId);
        TextView totalPrice=findViewById(R.id.totalPrice);
        TextView detail=findViewById(R.id.detail);
        TextView time=findViewById(R.id.time);
        id.setText(result.get(0).get(0));
        time.setText(orderTime);
        totalPrice.setText("￥"+UserLogin.shoppingCart.getTotalPrice()+"");
        StringBuilder sb=new StringBuilder();
        sb.append("\r\n");
        for(int i=0;i<result.size();i++){
            ArrayList<String> a=result.get(i);
            sb.append(i+1+". 商品名："+a.get(3)+"   "+"￥"+a.get(5)+"x"+a.get(4)+"   销售方id："+a.get(2)+"\r\n");
        }
        detail.setText(sb.toString());
    }
    ArrayList<ArrayList<String>> result;
    private ArrayList<ArrayList<String>> getOrderDetailById(final String orderId){
        Thread t=new Thread(){
            @Override public void run(){
                DBUtil dbUtil=new DBUtil();
                result=dbUtil.queryOrdersDetail(orderId);
            }
        };
        t.start();
        //还没查完就return
        try{
            Thread.sleep(100);
        }catch (Exception e){

        }
        return result;
    }
}

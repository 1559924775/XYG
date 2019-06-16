package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Goods_user_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_user_detail);

        //取出商品信息
        final Intent intent=getIntent();
        String detail=intent.getStringExtra("detail");//取得值
        String goods_detail[] =detail.split("&");
        final String goodsId=goods_detail[0];
        //构造页面
        final TextView name=findViewById(R.id.goodsName);
        name.setText(goods_detail[1]);
        final TextView price=findViewById(R.id.price);
        price.setText(goods_detail[3]+"￥");
        final TextView describ=findViewById(R.id.describ);
        describ.setText(goods_detail[4]);
        //取出购物车
       // final ShoppingCart shoppingCart=(ShoppingCart)intent.getSerializableExtra("shoppingCart");

        Button put=findViewById(R.id.putInCart);
        put.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Goods g=Goods_user_detail.this.getGoodsById(goodsId);
                Log.d("Tag","点击加入购物车");
                //点击加入购物车按钮加入购物车
                UserLogin.shoppingCart.add(goodsId,g);
                Log.d("Tag",UserLogin.shoppingCart.showGoods().toString());
                finish();
            }
        });

    }
    Goods goods;
    private Goods getGoodsById(final String id){
        Thread t=new Thread(){
            @Override public void run(){
                DBUtil dbUtil=new DBUtil();
                goods=dbUtil.getGoodsById(id);
            }
        };
        t.start();
        try{
            Thread.sleep(200);
        }catch (Exception e){}
        return goods;
    }
}

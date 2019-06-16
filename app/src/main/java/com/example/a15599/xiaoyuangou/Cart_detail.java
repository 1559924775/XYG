package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Cart_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);


        //取出商品信息
        final Intent intent=getIntent();
        String detail=intent.getStringExtra("detail");//取得值
        final String goods_detail[] =detail.split("&");
        final String goodsId=goods_detail[0];
        //构造页面
        final TextView name=findViewById(R.id.goodsName);
        name.setText(goods_detail[1]);
        final TextView price=findViewById(R.id.price);
        price.setText(goods_detail[3]+"￥");
        final TextView describ=findViewById(R.id.describ);
        describ.setText(goods_detail[4]);
        final EditText num=findViewById(R.id.num);
        num.setText(goods_detail[5]);

        Button update=findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogin.shoppingCart.update(goodsId,num.getText().toString());
                //resultCode=0
                setResult(0);
                finish();
            }
        });
        Button delete=findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserLogin.shoppingCart.remove(goodsId);
                //resultCode=0
                setResult(0);
                finish();
            }
        });

    }
}

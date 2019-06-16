package com.example.a15599.xiaoyuangou;
/**
 * 商家商品detail
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Goods_detail extends AppCompatActivity {
    boolean sucessful=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        //取出商品信息
        Intent intent=getIntent();
        String detail=intent.getStringExtra("detail");//取得值
        String goods_detail[] =detail.split("&");
        //构造页面
        TextView id=findViewById(R.id.goodsID);
        final int goodsID=Integer.parseInt(goods_detail[0]);
        id.setText(goodsID+"");
        TextView sellerId=findViewById(R.id.sellerID);
        sellerId.setText(goods_detail[5]);
        final EditText name=findViewById(R.id.goodsName);
        name.setText(goods_detail[1]);
        final EditText price=findViewById(R.id.price);
        price.setText(goods_detail[3]+"￥");
        final EditText describ=findViewById(R.id.describ);
        describ.setText(goods_detail[4]);

        //操作商品
        Button delete=findViewById(R.id.deletegoods);
        Button update=findViewById(R.id.updategoods);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thread t=new Thread(){
                    @Override public void run(){
                        DBUtil dbUtil=new DBUtil();
                        sucessful=dbUtil.deleteGoodsById(goodsID);
                        if(sucessful){
                            setResult(0);
                            finish();

                        }
                    }
                };
                t.start();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] newDetail=new String[3];
                newDetail[0]=name.getText().toString();
                newDetail[1]=price.getText().toString().split("￥")[0];
                newDetail[2]=describ.getText().toString();
                //更细商品信息
                Thread t=new Thread(){
                    @Override public void run(){
                        DBUtil dbUtil=new DBUtil();
                        sucessful=dbUtil.updateGoodsById(goodsID,newDetail);
                        if(sucessful){
                            // Toast.makeText(Goods_detail.this,"删除成功",Toast.LENGTH_SHORT).show();
                            setResult(0);
                            finish();
                        }
                    }
                };
                t.start();
            }
        });

    }

}

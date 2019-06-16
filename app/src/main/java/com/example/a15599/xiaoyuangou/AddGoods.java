package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

public class AddGoods extends AppCompatActivity {
    boolean registerSuccessfu=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);

        //取出商户id
        Intent intent=getIntent();
        final String sellerID=intent.getStringExtra("sellerID");
        final Goods goods=new Goods();
        final EditText goodsName=findViewById(R.id.goodsName);
        //图片路径
        final EditText price=findViewById(R.id.price);
        final EditText describ1=findViewById(R.id.describ1);

        Button add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goods.setName(goodsName.getText().toString());
                goods.setSrc("F:\\Desktop\\校园购\\ImagesDB\\a.PNG");
                BigDecimal p = new BigDecimal(price.getText().toString());
                goods.setPrice(p);
                String describ = describ1.getText().toString() ;
                goods.setDescrib(describ);
                goods.setSellerId(sellerID);
                Log.d("Tag", goods.toString());
                //写入数据库
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        DBUtil dbUtil = new DBUtil();
                        Log.d("Tag", goods.toString());
                        registerSuccessfu = dbUtil.update(goods);
                        //Toast.makeText(AddGoods.this,"商品已上架", Toast.LENGTH_LONG).show();
                        if (registerSuccessfu) {
                            finish();
                        }
                    }
                };
                t.start();
            }
        });
    }
}

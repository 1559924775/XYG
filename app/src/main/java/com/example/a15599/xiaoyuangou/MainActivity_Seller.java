package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity_Seller extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__seller);

        //取出商户id
        Intent intent=getIntent();
        final String sellerID=intent.getStringExtra("sellerID");
        Button add=findViewById(R.id.add);
        Button look=findViewById(R.id.look);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //转到添加商品页面
                Intent intent=new Intent(MainActivity_Seller.this,AddGoods.class);
                intent.putExtra("sellerID",sellerID);
                startActivity(intent);
            }
        });

        look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //传到浏览商品页面
                Intent intent=new Intent(MainActivity_Seller.this,MyGoods.class);
                intent.putExtra("sellerID",sellerID);
                startActivity(intent);
            }
        });
    }
}

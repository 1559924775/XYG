package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modelselect);

        //获取两个按钮
        Button user=findViewById(R.id.user);
        Button seller=findViewById(R.id.seller);
        //注册监听
        //1.用户
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ModelSelect.this,UserLogin.class);
                startActivity(intent);
            }
        });
        //2.商户
        seller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ModelSelect.this,SellerLogin.class);
                startActivity(intent);
            }
        });
    }
}

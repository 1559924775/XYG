package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SellerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_login);
        Button register=findViewById(R.id.register);
        Button login=findViewById(R.id.login);

        //商户注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "nihao");
                Intent intent = new Intent(SellerLogin.this, SellerRegister.class);
                startActivity(intent);
            }
        });
        //登录验证
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameText=findViewById(R.id.name);
                EditText pwdText=findViewById(R.id.pwd);
                String name=nameText.getText().toString();
                String pwd=pwdText.getText().toString();
                final Seller seller = new Seller();
                seller.setName(name);
                seller.setPwd(pwd);
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        DBUtil dbUtil = new DBUtil();
                        String id=dbUtil.check(seller);
                        if(!"-999".equals(id)){
                            //进入商家主界面
                            Log.d("Tag","登录成功");
                            Intent intent = new Intent(SellerLogin.this, MainActivity_Seller.class);
                            intent.putExtra("sellerID",id);
                            startActivity(intent);
                        }
                    }
                };
                t.start();
            }
        });
    }
}

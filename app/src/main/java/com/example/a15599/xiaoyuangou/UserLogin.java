package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.TimeUnit;

public class UserLogin extends AppCompatActivity {
     static ShoppingCart shoppingCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);
        Button register=findViewById(R.id.register);
        Button login=findViewById(R.id.login);

        //注册
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Tag", "nihao");
                Intent intent = new Intent(UserLogin.this, UserRegister.class);
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
                final User user = new User();
                user.setName(name);
                user.setPwd(pwd);
                Thread t = new Thread() {
                    @Override
                    public void run() {
                        DBUtil dbUtil = new DBUtil();
                        String id=dbUtil.check(user);

                        if(!"-999".equals(id)){
                            //进入购物主界面
                            Log.d("Tag","登录成功");
                            Intent intent = new Intent(UserLogin.this, MainActivity_user.class);
                            intent.putExtra("userId",id);
                            //为该用户创建购物车
                            shoppingCart=new ShoppingCart(id);
                            Log.d("Tag",shoppingCart.toString());
                            startActivity(intent);
                        }
                    }
                };
                t.start();
            }
        });
    }
}

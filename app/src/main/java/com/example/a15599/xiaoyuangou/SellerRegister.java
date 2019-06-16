package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SellerRegister extends AppCompatActivity {
    boolean registerSuccessfu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activityr_register_seller);

        Button back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Button submit=findViewById(R.id.register);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameText=findViewById(R.id.name);
                EditText repwdText=findViewById(R.id.repwd);
                EditText pwdText=findViewById(R.id.pwd);
                EditText telText=findViewById(R.id.tel);
                EditText emailText=findViewById(R.id.email);
                String name=nameText.getText().toString();
                String pwd=pwdText.getText().toString();
                String repwd=repwdText.getText().toString();
                String tel=telText.getText().toString();
                String email=emailText.getText().toString();
                if(pwd.equals(repwd)) {
                    final Seller seller = new Seller();
                    seller.setName(name);
                    seller.setPwd(pwd);
                    seller.setEmail(email);
                    seller.setTel(tel);
                    Log.d("Tag", seller.toString());
                    Thread t = new Thread() {
                        @Override
                        public void run() {
                            DBUtil dbUtil = new DBUtil();
                            Log.d("Tag", "开始");
                            registerSuccessfu = dbUtil.update(seller);
                            if (registerSuccessfu) {
                                finish();
                            }
                        }
                    };
                    t.start();
                }
            }
        });
    }
}

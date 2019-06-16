package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserInfo_update extends AppCompatActivity {
    User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_update);
        final Button store=findViewById(R.id.store);
        //获取user
        Intent intent=getIntent();
        user=(User)intent.getSerializableExtra("user");
        Log.d("Tag",user.toString());

        TextView id=findViewById(R.id.userId);
        final EditText name=findViewById(R.id.userName);
        final EditText tel=findViewById(R.id.tel);
        final EditText email=findViewById(R.id.email);
        final EditText adress=findViewById(R.id.adress);


        if(user!=null){

            id.setText("    "+user.getId()+"");

            name.setText(user.getName()+"");

            tel.setText(user.getTel()+"");

            email.setText(user.getEmail()+"");


            adress.setText(user.getAdress()+"");
        }

        store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent();
                user.setName(name.getText().toString());
                user.setTel(tel.getText().toString());
                user.setEmail(email.getText().toString());
                user.setAdress(adress.getText().toString());
                Thread t=new Thread(){
                    @Override public void run(){
                        DBUtil dbUtil=new DBUtil();
                        dbUtil.updateUserInfo(user);
                    }
                };
                t.start();
                //防止还没更新又去查
                try{
                    Thread.sleep(100);
                }catch (Exception e){

                }

                setResult(0);
                finish();
            }
        });

    }
}

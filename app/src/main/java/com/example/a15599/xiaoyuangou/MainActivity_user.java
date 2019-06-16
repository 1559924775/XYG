package com.example.a15599.xiaoyuangou;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity_user extends AppCompatActivity {
    FragmentManager fragmentManager=getFragmentManager();
    FragmentTransaction transaction=fragmentManager.beginTransaction();
    FirstFragment first1=new FirstFragment();
    CartFragment cart1=new CartFragment();
    OrdersFragment orders1=new OrdersFragment();
    AccountFragment account1=new AccountFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_main);

        Intent intent=getIntent();
        String userId=intent.getStringExtra("userId");


        //构建页面
        transaction.replace(R.id.up,first1);
        transaction.commit();

        Button first=findViewById(R.id.first);
        final Button cart=findViewById(R.id.cart);
        Button orders=findViewById(R.id.orders);
        Button account=findViewById(R.id.account);

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager=getFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.up,first1);
                transaction.commit();
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager=getFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.up,cart1);
                transaction.commit();
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager=getFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.up,orders1);
                transaction.commit();
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager=getFragmentManager();
                transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.up,account1);
                transaction.commit();
            }
        });
    }
}

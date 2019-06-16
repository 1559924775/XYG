package com.example.a15599.xiaoyuangou;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AccountFragment extends Fragment {
    String userId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityResult(int requestCode ,int resultCode,Intent data){
        if(requestCode==1){
            if(resultCode==0){
                Log.d("Tag","你好++++++++++++++"+user.toString());
                user=getUserInfo();
                initPage();
            }
        }

    }
    TextView id;
    TextView name;
    TextView tel;
    TextView email;
    TextView adress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.account,null);
        final Button update=view.findViewById(R.id.update);
        //获取user
        Intent intent=getActivity().getIntent();
        userId =intent.getStringExtra("userId");
        id=view.findViewById(R.id.userId);
        name=view.findViewById(R.id.userName);
        tel=view.findViewById(R.id.tel);
        email=view.findViewById(R.id.email);
        adress=view.findViewById(R.id.adress);
        getUserInfo();
        initPage();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),UserInfo_update.class);
                intent.putExtra("user",user);
                startActivityForResult(intent,1);

            }
        });

        return view;
    }
    private void initPage(){
        if(user!=null){
            id.setText("    "+user.getId()+"");
            name.setText(user.getName()+"");
            tel.setText(user.getTel()+"");
            email.setText(user.getEmail()+"");
            adress.setText(user.getAdress()+"");
        }

    }
    User user=null;
    private User getUserInfo(){

        Thread t=new Thread(){
            @Override public void run(){
                DBUtil dbUtil=new DBUtil();
                user=dbUtil.getUserById(userId);
            }
        };
        t.start();
        try{
            Thread.sleep(200);
        }catch (Exception e){

        }
        Log.d("Tag",user.toString());
       return user;
    }
}


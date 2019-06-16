package com.example.a15599.xiaoyuangou;
/**
 * 商家的浏览商品页面
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.mysql.jdbc.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyGoods extends AppCompatActivity {
    SimpleAdapter simpleAdapter;
    ListView listView;
    ArrayList<Map<String,String>> datas=new ArrayList<>();
    Map<String,String> map;
    String sellerId;
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        //跳转过去的Activity返回时（finish();时)自动调用该方法
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){//判断哪个activity传回
            if(resultCode==0){//判断何种回应方式
                Log.d("Tag","&&&&&&&&&&&&&&&&&&&&&&&&&&&&&:");
                initData();
                simpleAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_goods);

        //获取商户id
        Intent inten=getIntent();
        sellerId=inten.getStringExtra("sellerID");
        //构造页面
        initData();
        simpleAdapter=new SimpleAdapter(this,datas,R.layout.goods_item,
                new String[]{"img","text"},//map的key
                new int[]{R.id.imageView,R.id.TestView});//map的value的id
        listView=findViewById(R.id.listView);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("Tag", datas.get(position).get("id").toString());
//                Log.d("Tag","第"+id+"个");
                //进入商品详情页面

                StringBuilder sb=new StringBuilder();
                //重用result，防止再查数据库
                int index=(int)id;
                for(int i=0;i<result.get(index).size();i++){
                    if(i==result.get(index).size()-1){
                        sb.append(result.get(index).get(i));
                    }else{
                        sb.append(result.get(index).get(i)+"&");
                    }
                }
                String detail=sb.toString();
                Log.d("Tag",detail);
                Intent intent=new Intent(MyGoods.this,Goods_detail.class);
                intent.putExtra("detail",detail);
                startActivityForResult(intent,1);
            }
        });

    }
    private void initData(){
        ArrayList<ArrayList<String>> goodsSet=getGoods();
        Log.d("Tag",goodsSet.toString());
        datas.removeAll(datas);
        for(int i=0;i<goodsSet.size();i++) {
            ArrayList<String> aa=goodsSet.get(i);
            Log.d("Tag"," ^^^^^^^^^^^^^^"+aa.get(4));
            map = new HashMap<>();
//            map.put("b1","改");
//            map.put("b2","删");
            map.put("id",aa.get(0)+"");
            map.put("img", R.drawable.a+"");
            String text="商品名称："+aa.get(1)+"  单价："+aa.get(3)+"\r\n"+"商品描述："+aa.get(4);
            map.put("text", text);
            datas.add(map);
        }
    }
    ArrayList<ArrayList<String>> result=null;
    private ArrayList<ArrayList<String>> getGoods(){
        Thread t=new Thread(){
            @Override public void run(){
                DBUtil dbUtil=new DBUtil();
                result=dbUtil.queryGoodsBySeller(sellerId);

            }
        };
        t.start();
        //还没查完就return
        try{
            Thread.sleep(100);
        }catch (Exception e){

        }
        return result;
    }
}

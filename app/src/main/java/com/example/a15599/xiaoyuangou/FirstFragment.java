package com.example.a15599.xiaoyuangou;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FirstFragment extends Fragment {
    SimpleAdapter simpleAdapter;
    ListView listView;
    ArrayList<Map<String,String>> datas=new ArrayList<>();
    Map<String,String> map;
    ArrayList<ArrayList<String>> result;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.frist,null);
        final ListView listView= view.findViewById(R.id.list_item);

        //构造页面
        initData();
        simpleAdapter=new SimpleAdapter(getActivity(),datas,R.layout.goods_item,
                new String[]{"img","text"},//map的key
                new int[]{R.id.imageView,R.id.TestView});//map的value的id
        listView.setAdapter(simpleAdapter);

        //商品详情
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                StringBuilder sb=new StringBuilder();
                //重用goodsSet，防止再查数据库
                int index=(int)id;
                for(int i=0;i<goodsSet.get(index).size();i++){
                    if(i==goodsSet.get(index).size()-1){
                        sb.append(goodsSet.get(index).get(i));
                    }else{
                        sb.append(goodsSet.get(index).get(i)+"&");
                    }
                }
                String detail=sb.toString();
                Log.d("Tag",detail);
                Intent intent=new Intent(getActivity(),Goods_user_detail.class);
                intent.putExtra("detail",detail);
               // intent.putExtra("shoppingCart",MainActivity_user.shoppingCart);
               startActivity(intent);
                //startActivityForResult(intent,1);
            }
        });


        Button search= view.findViewById(R.id.search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),SearchActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
    ArrayList<ArrayList<String>> goodsSet;
    private void initData(){
        goodsSet=getGoods();
        ininData(goodsSet);

    }
    private void ininData(ArrayList<ArrayList<String>> goodsSet){
        datas.removeAll(datas);
        for(int i=0;i<goodsSet.size();i++) {
            ArrayList<String> aa = goodsSet.get(i);
            map = new HashMap<>();
//            map.put("b1","改");
//            map.put("b2","删");
            map.put("id", aa.get(0) + "");
            map.put("img", R.drawable.a + "");
            String text = "商品名称：" + aa.get(1) + "  单价：" + aa.get(3) + "\r\n" + "商品描述：" + aa.get(4);
            map.put("text", text);
            datas.add(map);
        }
    }
    private ArrayList<ArrayList<String>> getGoods(){


        Thread t=new Thread(){
            @Override public void run(){
                DBUtil dbUtil=new DBUtil();
                result=dbUtil.queryGoods();
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

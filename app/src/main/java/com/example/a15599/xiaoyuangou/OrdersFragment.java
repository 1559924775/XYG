package com.example.a15599.xiaoyuangou;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrdersFragment extends Fragment {
    SimpleAdapter simpleAdapter=null;
    ArrayList<Map<String,String>> datas=new ArrayList<>();
    Map<String,String> map;
    ListView listView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.orders,null);
        listView=view.findViewById(R.id.list);

        //构造页面
        initData();
        simpleAdapter=new SimpleAdapter(getActivity(),datas,R.layout.goods_item,
                new String[]{"img","text"},//map的key
                new int[]{R.id.imageView,R.id.TestView});//map的value的id
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
                String orderId=datas.get(i).get("id")+"";
                String orderTime=datas.get(i).get("time")+"";
                Intent intent=new Intent(getActivity(),Order_detail.class);
                intent.putExtra("orderId",orderId);
                intent.putExtra("orderTime",orderTime);
                startActivity(intent);
            }
        });

        return view;
    }
    private void initData(){
        orderSet=getOrders();
        ininData(orderSet);

    }
    private void ininData(ArrayList<ArrayList<String>> orders){
        datas.removeAll(datas);
        for(int i=0;i<orders.size();i++) {
            ArrayList<String> aa = orders.get(i);
            map = new HashMap<>();
            map.put("time",aa.get(6)+"");
            map.put("id", aa.get(0) + "");
            map.put("img", R.drawable.otu + "");
            String text = "订单号：" + aa.get(0) + "  总价：￥" + aa.get(4) + "\r\n" + "支付状态： " + aa.get(5)
                    +"\r\n"+"时间： "+aa.get(6);
            map.put("text", text);
            datas.add(map);
        }
    }
    ArrayList<ArrayList<String>> orderSet;
    ArrayList<ArrayList<String>> result;
    private ArrayList<ArrayList<String>> getOrders(){
        Thread t=new Thread(){
            @Override public void run(){
                DBUtil dbUtil=new DBUtil();
                result=dbUtil.queryOrders();
            }
        };
        t.start();
        //还没查完就return
        try{
            Thread.sleep(300);
        }catch (Exception e){

        }
        return result;
    }
}

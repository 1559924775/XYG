package com.example.a15599.xiaoyuangou;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {
    SimpleAdapter simpleAdapter;
    ListView listView;
    ArrayList<Map<String,String>> datas=new ArrayList<>();
    Map<String,String> map;
    ArrayList<ArrayList<String>> result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final ListView listView= findViewById(R.id.list_item);

        //构造页面
        initData();
        simpleAdapter=new SimpleAdapter(SearchActivity.this,datas,R.layout.goods_item,
                new String[]{"img","text"},//map的key
                new int[]{R.id.imageView,R.id.TestView});//map的value的id
        listView.setAdapter(simpleAdapter);

        //搜索功能
        Button back= findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //放回原页面
                finish();
            }
        });
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
               Intent intent=new Intent(SearchActivity.this,Goods_user_detail.class);
                intent.putExtra("detail",detail);
                startActivity(intent);
                //startActivityForResult(intent,1);
            }
        });
        SearchView search= findViewById(R.id.search);
        search.setIconifiedByDefault(false);//设置该搜索框默认是否自动缩小为图标。
        search.setSubmitButtonEnabled(true);//设置是否显示搜索按钮。
        CharSequence hint="输入商品名搜索";
        search.setQueryHint(hint);//设置搜索框内默认显示的提示文本。
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String s) {
                //开始搜索
                Thread t=new Thread(){
                    @Override public void run(){
                        DBUtil dbUtil=new DBUtil();
                        ArrayList<ArrayList<String>> searchSet=dbUtil.queryByGoodsName(s);
                        if(searchSet!=null){
                            ininData(searchSet);
                        }
                    }
                };
                t.start();
                try{
                    Thread.sleep(200);
                }catch (Exception e){

                }
                simpleAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                return false;
            }
        });
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

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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CartFragment extends Fragment{
    SimpleAdapter simpleAdapter;
    ArrayList<Goods> goodsList;
    ArrayList<Map<String,String>> datas=new ArrayList<>();
    Map<String,String> map;
    String cartTotalPrice;
   String userId;
    TextView totalPrice;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        //跳转过去的Activity返回时（finish();时)自动调用该方法
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1){//判断哪个activity传回
            if(resultCode==0){//判断何种回应方式
                Log.d("Tag","好啊---------------------------");
                initData();
                simpleAdapter.notifyDataSetChanged();
                cartTotalPrice=UserLogin.shoppingCart.getTotalPrice()+"";
                totalPrice.setText("                  总价： ￥"+cartTotalPrice);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.cart,null);
        final ListView listView= view.findViewById(R.id.listView);

        View item_view=inflater.inflate(R.layout.cart_item,null);

        //获取userId
        Intent intent=getActivity().getIntent();
        userId=intent.getStringExtra("userId");
        //构造页面
        initData();
        simpleAdapter=new SimpleAdapter(getActivity(),datas,R.layout.cart_item,
                new String[]{"img","text"},//map的key
                new int[]{R.id.imageView,R.id.TestView});//map的value的id
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int index=(int)id;
                Goods goods=goodsList.get(index);
                //这里detail中包括商品id
                String s=goods.getId()+"&"+goods.getName()+"&"+goods.getSrc()+"&"+goods.getPrice()+"&"+goods.getDescrib()
                        +"&"+goods.getNum();
                String detail=s;
                Log.d("Tag",detail);
                Intent intent=new Intent(getActivity(),Cart_detail.class);
                intent.putExtra("detail",detail);
                startActivityForResult(intent,1);
            }
        });
        cartTotalPrice=UserLogin.shoppingCart.getTotalPrice()+"";
        totalPrice=view.findViewById(R.id.totalPrice);
        totalPrice.setText("                  总价： ￥"+cartTotalPrice);
        final Order order=new Order();
        Button addOrder=view.findViewById(R.id.order);

        addOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = getUserInfo();
                Log.d("你好",user.getAdress().toString());
               // Log.d("Tag",user.getAdress()+"    ++++++++++++++++");
                if(!user.getAdress().equals(" ")){
                    if(!cartTotalPrice.equals("0.00")) {
                        //创建订单


                        order.setUserId(userId);
                        order.setpStatus(true);
                        order.setGoods(goodsList);
                        order.setTatalPrice(cartTotalPrice);
                        order.setUserTel(user.getTel());
                        order.setUserMail(user.getEmail());
                        Log.d("Tag", "下订单");
                        //下单，把订单存入数据库
                        Thread t = new Thread() {
                            @Override
                            public void run() {
                                DBUtil dbUtil = new DBUtil();
                                dbUtil.order(order);
                                try{
                                    TimeUnit.MICROSECONDS.sleep(200);
                                }catch(Exception e){}

                            }
                        };
                        t.start();
                        UserLogin.shoppingCart = new ShoppingCart();
                        initData();
                        simpleAdapter.notifyDataSetChanged();
                        totalPrice.setText("                  总价： ￥0.00");
                        android.widget.Toast.makeText(getActivity(), "下单成功", Toast.LENGTH_SHORT).show();
                    }
            }else {
                     android.widget.Toast.makeText(getActivity(), "请先填写收货地址！", Toast.LENGTH_SHORT).show();
                }


            }
        });

        return view;
    }
    private void initData(){
        goodsList=showCart();
        Log.d("Tag",goodsList.toString());
        ininData(goodsList);

    }
    private void ininData(ArrayList<Goods> goodsSet){
        datas.removeAll(datas);
        for(int i=0;i<goodsSet.size();i++) {
            map = new HashMap<>();
//            map.put("b1","改");
//            map.put("b2","删");
            map.put("id", goodsSet.get(i).getId() + "");
            map.put("img", R.drawable.a + "");
            String text = "商品名称：" + goodsSet.get(i).getName() +
                    "  单价：" + goodsSet.get(i).getPrice() + "\r\n" + "商品描述：" + goodsSet.get(i).getDescrib()
                    +"\r\n"+"购买数量：x"+goodsSet.get(i).getNum();
            map.put("text", text);
            datas.add(map);
        }
    }
    private ArrayList<Goods> showCart(){
        ShoppingCart shoppingCart=UserLogin.shoppingCart;
        return shoppingCart.showGoods();

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
            Thread.sleep(400);
        }catch (Exception e){

        }
        Log.d("Tag",user.toString());
        return user;
    }
}

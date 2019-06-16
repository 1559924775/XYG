package com.example.a15599.xiaoyuangou;

import android.util.Log;

import java.sql.*;
import java.util.ArrayList;

/**
 * 这是一个帮助下订单的类
 */
public class OrderService {
    Connection connection=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;
    int orderId=0;
    public OrderService(){
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void confirmOrder(Order order){
       //得到链接
        connection=getConnection();
        try {
            connection.setAutoCommit(false);
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            String sql="insert into orders (userId,userTel,userMail,totalPrice,pStatus,dTime) values(?,?,?,?,?,now());";
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setObject(1,order.getUserId()+"");
            preparedStatement.setObject(2,order.getUserTel()+"");
            preparedStatement.setObject(3,order.getUserMail()+"");
            preparedStatement.setObject(4,order.getTatalPrice()+"");
            preparedStatement.setBoolean(5,order.getpStatus());
            preparedStatement.executeUpdate();
            //查询某表自增长字段值
            sql="select last_insert_id();";
            preparedStatement=connection.prepareStatement(sql);
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next())
                orderId=resultSet.getInt(1);
            ArrayList<Goods> goods=order.getGoods();

            for(int i=0;i<goods.size();i++){
                sql="insert into order_item (orderid , goodsId , sellerId,goodsName,num , price) values(?,?,?,?,?,?)";
                preparedStatement=connection.prepareStatement(sql);
                preparedStatement.setInt(1,orderId);
                preparedStatement.setInt(2, goods.get(i).getId());
                preparedStatement.setObject(3, goods.get(i).getSellerId());
                preparedStatement.setObject(4,goods.get(i).getName());
                preparedStatement.setObject(5,goods.get(i).getNum());
                preparedStatement.setObject(6,goods.get(i).getPrice());
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                throw new RuntimeException(e);
            }
        }finally{
            close(resultSet, preparedStatement, connection);
        }
    }


    private static String driverName="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://10.0.2.2:3306/xyg?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
    private static String user="root";
    private static String pwd="123456";
    private Connection getConnection(){
        Connection connection=null;
        try {
            connection = DriverManager.getConnection(url,user,pwd);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("Tag","无法连接");
        }
        return connection;
    }
    private void close(ResultSet resultSet, Statement statement, Connection connection){
        try {
            if(resultSet!=null)resultSet.close();
            if(statement!=null)statement.close();
            if(connection!=null)connection.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


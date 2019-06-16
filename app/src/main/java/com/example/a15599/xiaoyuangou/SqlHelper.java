package com.example.a15599.xiaoyuangou;

/**
 * Created by 15599 on 2018/4/4.
 */

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SqlHelper {
    public static String uri="47.110.255.222";
    public static void main(String[] args){
        String sql="insert into users (name,address) values('ef','ewfw');";
        new SqlHelper().update1(sql,null);
    }
    private static String driverName="com.mysql.jdbc.Driver";
    private  static String url=null;
    private static String user="admin";
    private static String pwd="123456";

    public SqlHelper(){
        url="jdbc:mysql://47.110.255.222:3306/xyg?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        try {
            Class.forName(driverName);
            Log.d("Tag","进入");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("Tag","找不到");
        }
    }
    private Connection getConnection(){
        Connection connection=null;
        try {Log.d("URL",url);
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
    //查询、
    public ArrayList<ArrayList<String>> query(String sql, String...param){
        ArrayList<ArrayList<String>> result=new ArrayList<>();
        Connection connection=getConnection();
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            if(param!=null){
                for(int i=0;i<param.length;i++){
                    preparedStatement.setObject(i+1, param[i]);
                }
            }
            resultSet=preparedStatement.executeQuery();
            ResultSetMetaData rsmd=resultSet.getMetaData();
            int columnCount=rsmd.getColumnCount();
            while(resultSet.next()){
                ArrayList<String> aa=new ArrayList<>();
                for(int i=0;i<columnCount;i++){
                    String s=resultSet.getObject(i+1)+"";
                    aa.add(s);
                }
                result.add(aa);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            close(resultSet,preparedStatement,connection);
        }
        return result;
    }
    //更新（不含事务)
    public void update1(String sql, String...param){
        Connection connection=getConnection();
        PreparedStatement preparedStatement=null;
        try {
            preparedStatement=connection.prepareStatement(sql);
            if(param!=null){
                for(int i=0;i<param.length;i++){
                    preparedStatement.setObject(i+1, param[i]);
                }
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }finally{
            close(null,preparedStatement,connection);
        }
    }
    //更新（含事务)
    public void update2(String sql[], String[][] param){
        Connection connection=getConnection();
        PreparedStatement preparedStatement=null;
        try {
            connection.setAutoCommit(false);
            for(int i=0;i<sql.length;i++){
                preparedStatement=connection.prepareStatement(sql[i]);
                if(param[i]!=null){
                    for(int j=0;i<param[i].length;j++){
                        preparedStatement.setObject(i+1, param[i][j]);
                    }
                }
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }finally{
            close(null,preparedStatement,connection);
        }
    }
}


package com.example.a15599.xiaoyuangou;

import java.io.Serializable;

/**
 * Created by 15599 on 2018/4/4.
 */

public class User implements Serializable{


    public void setId(int id) {
        this.id = id;
    }

    private  int id;
    private String name;
    private String pwd;
    private String tel;
    private String email;
    private String adress=" " ;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String toString(){
        return "id:"+id+" name:"+name+" pwd:"+pwd+" tel:"+tel+" email:"+email;
    }


}

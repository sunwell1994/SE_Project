package com.naman14.timber.bean;


import cn.bmob.v3.BmobUser;


/**
 * Created by admin on 2015/12/27.
 */
public class MyUser extends BmobUser{
    /**
     * created by IvyHan
     * 2015/12/27
     * manage user data extending from BmobUser
     */

    private Boolean sex;
    private String nick;

    public boolean getSex(){
        return this.sex;
    }

    public void setSex(boolean sex){
        this.sex=sex;

    }

    public String getNick(){
        return this.nick;
    }

    public void setNick(String nick){
        this.nick=nick;
    }

}

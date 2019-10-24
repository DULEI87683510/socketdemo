package com.dl.socketdemo.user.entity;

/**
 *@ClassName User
 *@Description TODO
 *@Author DL
 *@Date 2019/10/18 17:09    
 *@Version 1.0
 */
public class User {
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                '}';
    }
}

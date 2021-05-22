package com.baoge.entity;

/**
 * @Author shaoxubao
 * @Date 2019/9/25 20:45
 */
public class User {

    private Integer id;

    private String userName;

    public Integer getId() {
        return id;
    }

    public User(Integer id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                '}';
    }
}

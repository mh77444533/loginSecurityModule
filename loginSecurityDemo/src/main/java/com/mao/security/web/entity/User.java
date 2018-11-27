package com.mao.security.web.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Past;
import java.util.Date;

public class User {
    public User(String id, String username, String password, Date birthday) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.birthday = birthday;
    }

    public User() {

    }

    private String id;

    private String username;

    @NotBlank(message = "密码不能为空")   //不为null
    private String password;

    @Past(message = "生日必须是过去的时间")  // 未来的时间
    private Date birthday;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

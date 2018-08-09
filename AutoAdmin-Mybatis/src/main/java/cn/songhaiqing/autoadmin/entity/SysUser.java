package cn.songhaiqing.autoadmin.entity;

import cn.songhaiqing.autoadmin.base.BaseEntity;

public class SysUser extends BaseEntity {
    // 账号
    private String account;
    // 密码
    private String password;
    // 姓名
    private String name;
    // 密码加密盐
    private String salt;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}
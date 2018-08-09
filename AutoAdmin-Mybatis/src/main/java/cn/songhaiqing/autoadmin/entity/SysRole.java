package cn.songhaiqing.autoadmin.entity;

import cn.songhaiqing.autoadmin.base.BaseEntity;

public class SysRole extends BaseEntity {

    private String name;
    private String des;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDes() {
        return des;
    }
    public void setDes(String des) {
        this.des = des;
    }
}
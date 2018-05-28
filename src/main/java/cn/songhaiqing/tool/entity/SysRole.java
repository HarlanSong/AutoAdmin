package cn.songhaiqing.tool.entity;

import cn.songhaiqing.tool.base.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;


@Entity
@Table(name = "sys_role")
@Where(clause = "deleted=0")
public class SysRole extends BaseEntity {
    // 名称
    @Column(name = "name")
    private String name;
    // 描述
    @Column(name = "des")
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
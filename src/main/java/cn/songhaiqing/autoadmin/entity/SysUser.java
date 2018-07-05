package cn.songhaiqing.autoadmin.entity;

import cn.songhaiqing.autoadmin.base.BaseEntity;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "sys_user")
@Where(clause = "deleted=0")
public class SysUser extends BaseEntity {
    // 账号
    @Column(name = "account")
    private String account;
    // 密码
    @Column(name = "password")
    private String password;
    // 姓名
    @Column(name = "name")
    private String name;
    // 密码加密盐
    @Column(name = "salt")
    private String salt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sysUser")
    private List<SysUserRole> sysUserRoles;


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

    public List<SysUserRole> getSysUserRoles() {
        return sysUserRoles;
    }

    public void setSysUserRoles(List<SysUserRole> sysUserRoles) {
        this.sysUserRoles = sysUserRoles;
    }
}
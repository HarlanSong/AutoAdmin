package cn.songhaiqing.autoadmin.base;

import javax.persistence.*;
import java.util.Date;


@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "create_time", nullable = false,updatable = false)
    private Date createTime = new Date();

    @Column(name = "update_time", nullable = false)
    private Date updateTime = new Date();

    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;

    public Date getCreateTime() {
        return createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}

package cn.songhaiqing.autoadmin.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import java.util.List;

public interface  BaseMapper<T> extends Mapper<T>,MySqlMapper<T> {

    List<T> findByPage();
}

package cn.songhaiqing.autoadmin.mapper;

import cn.songhaiqing.autoadmin.base.BaseMapper;
import cn.songhaiqing.autoadmin.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findByParentIdOrderByOrderNo(Long parentId);

    List<Menu> selectByPage();
}

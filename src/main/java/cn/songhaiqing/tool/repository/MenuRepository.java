package cn.songhaiqing.tool.repository;

import cn.songhaiqing.tool.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>,JpaSpecificationExecutor<Menu> {
    List<Menu> findByParentIdOrderByOrderNo(Long parentId);
}
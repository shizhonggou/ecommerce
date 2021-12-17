package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.ShelfResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ShelfResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShelfResource record);

    ShelfResource selectByPrimaryKey(Integer id);

    List<ShelfResource> selectAll();

    int updateByPrimaryKey(ShelfResource record);

    /**
     * @Description 更新资源数量
     * @Author zhaofy
     * @Date  2021/9/10
     * @Param [record]
     * @return int
     **/
    int updateResourceNumByPrimaryKey(ShelfResource record);

    /**
     * @Description 查询上架资源列表
     * @Author zhaofy
     * @Date  2021/8/23
     * @Param [record]
     * @return java.util.List<com.isechome.ecommerce.entity.ShelfResource>
     **/
    List<ShelfResource> selectListByResource(ShelfResource record);

    /**
     * @Description 查询上架资源列表（混合 品材规 查询）
     * @Author zhaofy
     * @Date  2021/9/8
     * @Param [record]
     * @return java.util.List<com.isechome.ecommerce.entity.ShelfResource>
     **/
    List<ShelfResource> selectListByMixResource(ShelfResource record);

    /**
     * @Description 查询未售出资源
     * @Author zhaofy
     * @Date  2021/8/26
     * @Param [wid]
     * @return double
     **/
    ShelfResource selectUnSoldNumByWid(ShelfResource shelfResource);

    /**
     * @Description 根据上架资源id获取对应的仓库资源id
     * @Author zhaofy
     * @Date  2021/9/1
     * @Param [id]
     * @return int
     **/
    Integer getResourceIdById(int id);

    /**
     * @Description 查看当天是否已经上架该资源 返回上架资源id
     * @Author zhaofy
     * @Date  2021/9/9
     * @Param [shelfResource]
     * @return int
     **/
    ShelfResource getTodayResourceID(Map<String,Object> selectMap);
}
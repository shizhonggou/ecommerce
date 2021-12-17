package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.WarehouseResource;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public interface WarehouseResourceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseResource record);

    WarehouseResource selectByPrimaryKey(Integer id);

    List<WarehouseResource> selectAll();

    int updateByPrimaryKey(WarehouseResource record);
    /**
     * @Description 上架资源时减掉仓库资源
     * @Author zhaofy
     * @Date  2021/8/27
     * @Param [record]
     * @return int
     **/
    int updateShangJiaByPrimaryKey(WarehouseResource record);

    WarehouseResource getIdBySth(WarehouseResource record);

    /**
     * @Description 搜索仓库资源列表
     * @Author zhaofy
     * @Date  2021/8/21
     * @Param [record]
     * @return java.util.List<com.isechome.ecommerce.entity.WarehouseResource>
     **/
    List<WarehouseResource> selectListByResource(WarehouseResource record);

    /**
     * @Description 更新预留资源的信息
     * @Author zhaofy
     * @Date  2021/8/21
     * @Param [record]
     * @return int
     **/
    int updateByResource(WarehouseResource record);

    /**
     * @Description 结算资源处理接口 资源返回仓库
     * @Author zhaofy
     * @Date  2021/9/1
     * @Param [record]
     * @return int
     **/
    int updateByReturnResource(WarehouseResource record);

    /**
     * @Description 审核所有仓库资源
     * @Author zhaofy
     * @Date  2021/8/21
     * @Param [record]
     * @return int
     **/
    int auditResource(WarehouseResource record);

    /**
     * @Description 查询已审核仓库资源
     * @Author zhaofy
     * @Date  2021/8/26
     * @Param [record]
     * @return java.util.List<com.isechome.ecommerce.entity.WarehouseResource>
     **/
    List<WarehouseResource> selectAuditListByResource(HashMap<String, Object> queryMap);

    /**
     * @Description 取消审核
     * @Author zhaofy
     * @Date  2021/9/3
     * @Param [record]
     * @return int
     **/
    int unReviewByPrimaryKey(WarehouseResource record);

    //将仓库资源预留资源和日期清空
    int updateReserved(int id);

    List<WarehouseResource> getAuditResourceId(WarehouseResource record);

}
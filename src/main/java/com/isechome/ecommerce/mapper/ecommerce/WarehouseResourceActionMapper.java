package com.isechome.ecommerce.mapper.ecommerce;

import com.isechome.ecommerce.entity.WarehouseResourceAction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseResourceActionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseResourceAction record);

    WarehouseResourceAction selectByPrimaryKey(Integer id);

    List<WarehouseResourceAction> selectAll();

    List<WarehouseResourceAction> selectByResource(WarehouseResourceAction record);

    int updateByPrimaryKey(WarehouseResourceAction record);
}